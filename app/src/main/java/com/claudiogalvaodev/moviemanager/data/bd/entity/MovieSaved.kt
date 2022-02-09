package com.claudiogalvaodev.moviemanager.data.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieSaved(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val movieId: Int,
    val moviePosterUrl: String,
    val myListId: Int
)