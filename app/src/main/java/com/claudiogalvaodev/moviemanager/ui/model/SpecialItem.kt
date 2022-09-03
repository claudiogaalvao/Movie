package com.claudiogalvaodev.moviemanager.ui.model


data class SpecialItem(
    val itemId: Int,
    val title: String,
    val subtitle: String,
    val type: String,
    val releaseDate: String,
    val imageUrl: String,
    val leastOneMovieId: Int,
    val categories: List<String>,
    val categoriesWinner: List<String>
)