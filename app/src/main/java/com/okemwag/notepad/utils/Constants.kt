package com.okemwag.notepad.utils

object Constants {
    const val DATABASE_NAME = "note_database"
    const val TABLE_NAME = "notes"
    const val PREFS_NAME = "notepad_prefs"

    // API Constants
    const val BASE_URL = "https://notepad.romino.xyz/api/v1/"
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L

    // UI Constants
    const val ANIMATION_DURATION = 300L
    const val DEBOUNCE_DELAY = 500L
    const val SEARCH_DELAY = 300L

    // Colors
    const val DEFAULT_NOTE_COLOR = 0xFFFFFFFF.toInt()
    val NOTE_COLORS = listOf(
        0xFFFFFFFF.toInt(), // White
        0xFFFFEB3B.toInt(), // Yellow
        0xFF4CAF50.toInt(), // Green
        0xFF2196F3.toInt(), // Blue
        0xFFFF9800.toInt(), // Orange
        0xFFE91E63.toInt(), // Pink
        0xFF9C27B0.toInt(), // Purple
        0xFF607D8B.toInt()  // Blue Grey
    )

    // Sync
    const val SYNC_INTERVAL_MINUTES = 30L
    const val CLEANUP_INTERVAL_DAYS = 30L
}
