package com.claudiogalvaodev.moviemanager.data.bd.converter

import androidx.room.TypeConverter
import com.claudiogalvaodev.moviemanager.utils.enums.OscarCategory
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromString(categories: String): List<OscarCategory> {
        return Gson().fromJson(categories, Array<OscarCategory>::class.java).asList()
    }

    @TypeConverter
    fun fromCategories(categories: List<OscarCategory>): String {
        return Gson().toJson(categories).orEmpty()
    }

}