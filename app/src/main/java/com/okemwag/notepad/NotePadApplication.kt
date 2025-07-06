package com.okemwag.notepad

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NotepadApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize any required components here
        // Example: Analytics, Crash reporting, etc.
    }
}