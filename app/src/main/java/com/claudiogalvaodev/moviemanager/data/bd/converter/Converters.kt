package com.claudiogalvaodev.moviemanager.data.bd.converter

import androidx.room.TypeConverter
import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromString(movies: String): List<Movie> {
        return Gson().fromJson(movies, Array<Movie>::class.java).asList()
    }

    @TypeConverter
    fun fromMovies(movies: List<Movie>): String {
        return Gson().toJson(movies).orEmpty()
    }

}