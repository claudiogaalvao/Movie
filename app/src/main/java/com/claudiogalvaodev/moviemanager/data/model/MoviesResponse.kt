package com.claudiogalvaodev.moviemanager.data.model

data class MoviesResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<Movie>
)