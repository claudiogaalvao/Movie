package com.claudiogalvaodev.moviemanager.data.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyList(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val name: String,
    val posterPath: String? = null
)
