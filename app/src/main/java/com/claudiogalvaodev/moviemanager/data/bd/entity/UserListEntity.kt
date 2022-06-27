package com.claudiogalvaodev.moviemanager.data.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.claudiogalvaodev.moviemanager.data.model.Movie

@Entity
data class UserListEntity(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val name: String,
    val movies: List<Movie> = emptyList()
)
