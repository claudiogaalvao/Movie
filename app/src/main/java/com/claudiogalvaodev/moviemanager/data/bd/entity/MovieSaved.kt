package com.claudiogalvaodev.moviemanager.data.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieSaved(
    @PrimaryKey(autoGenerate = false)
    val movieId: Int,
    val moviePosterUrl: String,
    val myListId: Int
)