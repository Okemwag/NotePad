package com.okemwag.notepad.data.remote

data class NoteDto(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val category: String?,
    val isFavorite: Boolean,
    val color: Int?,
    val userId: String
)

data class CreateNoteRequest(
    val title: String,
    val content: String,
    val category: String? = null,
    val isFavorite: Boolean = false,
    val color: Int? = null
)

data class UpdateNoteRequest(
    val title: String,
    val content: String,
    val category: String? = null,
    val isFavorite: Boolean = false,
    val color: Int? = null
)

data class SyncRequest(
    val notes: List<NoteDto>,
    val lastSyncTime: String
)

data class SyncResponse(
    val notes: List<NoteDto>,
    val deletedNoteIds: List<String>,
    val syncTime: String
)

// API Response wrapper
data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val message: String?,
    val error: String?
)