package com.claudiogalvaodev.filmes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteMovieEntity(
    @PrimaryKey
    val id: Int
)