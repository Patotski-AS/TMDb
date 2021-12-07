package com.example.android.tmdb

import android.app.Application
import androidx.preference.PreferenceManager
import com.example.android.tmdb.data.utill.setTheme
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TMDbApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.setTheme()
    }
}
