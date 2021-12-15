package com.claudiogalvaodev.moviemanager.model

import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieEntity

data class RequestCallback(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<MovieEntity>
)