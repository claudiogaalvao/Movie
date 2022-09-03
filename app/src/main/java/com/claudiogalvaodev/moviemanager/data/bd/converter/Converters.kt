package com.claudiogalvaodev.moviemanager.data.bd.converter

import androidx.room.TypeConverter
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromString(movies: String): List<MovieModel> {
        return Gson().fromJson(movies, Array<MovieModel>::class.java).asList()
    }

    @TypeConverter
    fun fromMovies(movieModels: List<MovieModel>): String {
        return Gson().toJson(movieModels).orEmpty()
    }

}