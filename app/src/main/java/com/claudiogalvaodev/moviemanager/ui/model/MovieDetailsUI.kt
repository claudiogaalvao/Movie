package com.claudiogalvaodev.moviemanager.ui.model

import com.claudiogalvaodev.moviemanager.data.model.Company
import com.claudiogalvaodev.moviemanager.data.model.Movie

data class MovieDetailsUI(
    val movie: Movie,
    val companies: List<Company>
)