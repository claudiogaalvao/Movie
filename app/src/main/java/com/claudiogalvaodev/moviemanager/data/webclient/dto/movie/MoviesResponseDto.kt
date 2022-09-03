package com.claudiogalvaodev.moviemanager.data.webclient.dto.movie

import com.google.gson.annotations.SerializedName

data class MoviesResponseDto(
    val page: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val results: List<MovieDto>
)