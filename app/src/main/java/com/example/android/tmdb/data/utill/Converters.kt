package com.example.android.tmdb.data.utill

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromList(value: List<Int>?): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toList(value: String): List<Int>? {
        if (value == "") return emptyList()
        return Json.decodeFromString(value)
    }
}
