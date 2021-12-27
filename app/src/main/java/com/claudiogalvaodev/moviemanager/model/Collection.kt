package com.claudiogalvaodev.moviemanager.model

data class Collection(
    val id: Int,
    val name: String,
    val poster_path: String,
    val backdrop_path: String,
    val parts: List<Movie>
)