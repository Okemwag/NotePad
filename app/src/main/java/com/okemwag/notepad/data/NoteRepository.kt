package com.okemwag.notepad.data


import com.okemwag.notepad.data.local.Note
import com.okemwag.notepad.data.local.NoteDao
import com.okemwag.notepad.data.local.SyncStatus
import com.okemwag.notepad.data.remote.NoteApiService
import com.okemwag.notepad.data.remote.CreateNoteRequest
import com.okemwag.notepad.data.remote.NoteDto
import com.okemwag.notepad.data.remote.UpdateNoteRequest
import com.okemwag.notepad.utils.NetworkHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import java.util.Date
import java.util.UUID

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val apiService: NoteApiService,
    private val networkHelper: NetworkHelper
) {

    // Local data operations
    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    fun getFavoriteNotes(): Flow<List<Note>> = noteDao.getFavoriteNotes()

    suspend fun getNoteById(id: String): Note? = noteDao.getNoteById(id)

    fun searchNotes(query: String): Flow<List<Note>> = noteDao.searchNotes(query)

    fun getNotesByCategory(category: String): Flow<List<Note>> = noteDao.getNotesByCategory(category)

    fun getAllCategories(): Flow<List<String>> = noteDao.getAllCategories()

    suspend fun insertNote(note: Note) {
        val noteWithId = note.copy(
            id = note.id.ifEmpty { UUID.randomUUID().toString() },
            syncStatus = SyncStatus.PENDING
        )
        noteDao.insertNote(noteWithId)

        // Try to sync if network is available
        if (networkHelper.isNetworkAvailable()) {
            syncNoteToRemote(noteWithId)
        }
    }

    suspend fun updateNote(note: Note) {
        val updatedNote = note.copy(
            updatedAt = Date(),
            syncStatus = SyncStatus.PENDING
        )
        noteDao.updateNote(updatedNote)

        // Try to sync if network is available
        if (networkHelper.isNetworkAvailable()) {
            syncNoteToRemote(updatedNote)
        }
    }

    suspend fun deleteNote(noteId: String) {
        noteDao.softDeleteNote(noteId)

        // Try to sync deletion if network is available
        if (networkHelper.isNetworkAvailable()) {
            syncDeleteToRemote(noteId)
        }
    }

    suspend fun toggleFavorite(noteId: String) {
        val note = noteDao.getNoteById(noteId)
        note?.let {
            val updatedNote = it.copy(
                isFavorite = !it.isFavorite,
                updatedAt = Date(),
                syncStatus = SyncStatus.PENDING
            )
            noteDao.updateNote(updatedNote)

            if (networkHelper.isNetworkAvailable()) {
                syncNoteToRemote(updatedNote)
            }
        }
    }

    // Remote sync operations
    private suspend fun syncNoteToRemote(note: Note) {
        try {
            val token = getAuthToken() ?: return

            val response = if (note.syncStatus == SyncStatus.LOCAL) {
                // Create new note on server
                apiService.createNote(
                    token = "Bearer $token",
                    note = CreateNoteRequest(
                        title = note.title,
                        content = note.content,
                        category = note.category,
                        isFavorite = note.isFavorite,
                        color = note.color
                    )
                )
            } else {
                // Update existing note on server
                apiService.updateNote(
                    token = "Bearer $token",
                    id = note.id,
                    note = UpdateNoteRequest(
                        title = note.title,
                        content = note.content,
                        category = note.category,
                        isFavorite = note.isFavorite,
                        color = note.color
                    )
                )
            }

            if (response.isSuccessful) {
                noteDao.updateSyncStatus(note.id, SyncStatus.SYNCED)
            }
        } catch (e: Exception) {
            // Handle sync error - note remains in PENDING status
            e.printStackTrace()
        }
    }

    private suspend fun syncDeleteToRemote(noteId: String) {
        try {
            val token = getAuthToken() ?: return

            val response = apiService.deleteNote(
                token = "Bearer $token",
                id = noteId
            )

            if (response.isSuccessful) {
                // Permanently delete from local database
                val note = noteDao.getNoteById(noteId)
                note?.let { noteDao.deleteNote(it) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun syncAllNotes() {
        if (!networkHelper.isNetworkAvailable()) return

        try {
            val token = getAuthToken() ?: return

            // Get all pending notes
            val pendingNotes = noteDao.getNotesBySyncStatus(SyncStatus.PENDING)

            // Sync each pending note
            pendingNotes.forEach { note ->
                syncNoteToRemote(note)
            }

            // Pull remote changes
            val response = apiService.getAllNotes("Bearer $token")
            if (response.isSuccessful) {
                response.body()?.let { remoteDtos ->
                    val remoteNotes = remoteDtos.map { it.toNote() }

                    // Update local database with remote changes
                    remoteNotes.forEach { remoteNote ->
                        val localNote = noteDao.getNoteById(remoteNote.id)
                        if (localNote == null || localNote.updatedAt.before(remoteNote.updatedAt)) {
                            noteDao.insertNote(remoteNote.copy(syncStatus = SyncStatus.SYNCED))
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getNotesCount(): Int = noteDao.getNotesCount()

    suspend fun cleanupOldDeletedNotes() {
        val thirtyDaysAgo = Date(System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000))
        noteDao.deleteOldDeletedNotes(thirtyDaysAgo)
    }

    private suspend fun getAuthToken(): String? {
        // TODO: Implement auth token retrieval from SharedPreferences or secure storage
        return null
    }
}

// Extension function to convert DTO to Entity
private fun NoteDto.toNote(): Note {
    return Note(
        id = this.id,
        title = this.title,
        content = this.content,
        createdAt = Date(), // TODO: Parse from string
        updatedAt = Date(), // TODO: Parse from string
        category = this.category,
        isFavorite = this.isFavorite,
        color = this.color,
        syncStatus = SyncStatus.SYNCED
    )
}
