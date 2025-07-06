package com.okemwag.notepad.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    val id: String,
    val title: String,
    val content: String,
    val createdAt: Date,
    val updatedAt: Date,
    val category: String? = null,
    val isFavorite: Boolean = false,
    val color: Int? = null,
    val isDeleted: Boolean = false,
    val syncStatus: SyncStatus = SyncStatus.LOCAL
)

enum class SyncStatus {
    LOCAL,      // Not synced to server
    SYNCED,     // Synced with server
    PENDING,    // Waiting to sync
    CONFLICT    // Sync conflict needs resolution
}