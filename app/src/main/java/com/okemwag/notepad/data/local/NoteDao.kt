package com.okemwag.notepad.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes WHERE isDeleted = 0 ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE isDeleted = 0 AND isFavorite = 1 ORDER BY updatedAt DESC")
    fun getFavoriteNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id AND isDeleted = 0")
    suspend fun getNoteById(id: String): Note?

    @Query("SELECT * FROM notes WHERE isDeleted = 0 AND (title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%') ORDER BY updatedAt DESC")
    fun searchNotes(query: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE isDeleted = 0 AND category = :category ORDER BY updatedAt DESC")
    fun getNotesByCategory(category: String): Flow<List<Note>>

    @Query("SELECT DISTINCT category FROM notes WHERE isDeleted = 0 AND category IS NOT NULL")
    fun getAllCategories(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<Note>)

    @Update
    suspend fun updateNote(note: Note)

    @Query("UPDATE notes SET isDeleted = 1, updatedAt = :deletedAt WHERE id = :id")
    suspend fun softDeleteNote(id: String, deletedAt: Date = Date())

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM notes WHERE isDeleted = 1 AND updatedAt < :before")
    suspend fun deleteOldDeletedNotes(before: Date)

    @Query("SELECT * FROM notes WHERE syncStatus = :status")
    suspend fun getNotesBySyncStatus(status: SyncStatus): List<Note>

    @Query("UPDATE notes SET syncStatus = :status WHERE id = :id")
    suspend fun updateSyncStatus(id: String, status: SyncStatus)

    @Query("SELECT COUNT(*) FROM notes WHERE isDeleted = 0")
    suspend fun getNotesCount(): Int

    @Query("UPDATE notes SET isFavorite = :favorite, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateFavoriteStatus(id: String, favorite: Boolean, updatedAt: Date = Date())
}