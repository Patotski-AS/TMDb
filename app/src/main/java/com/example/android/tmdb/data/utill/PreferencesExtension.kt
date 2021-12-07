package com.example.android.tmdb.data.utill

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import java.util.*

private const val DEFAULT_LANGUAGE = "en-US"
private const val RU_LANGUAGE = "ru-RU"
private const val PREF_LIST_SORT = "list_sort"
private const val DEFAULT_SORT = "popularity.desc"
private const val PREF_THEME = "theme"

fun SharedPreferences.setTheme() {
    when (this.getBoolean(PREF_THEME, false)) {
        false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}

fun SharedPreferences.getValueLanguage() = when (Locale.getDefault().language) {
    "ru" -> RU_LANGUAGE
    else -> DEFAULT_LANGUAGE
}

fun SharedPreferences.getValueSort() = this.getString(PREF_LIST_SORT, DEFAULT_SORT).toString()
