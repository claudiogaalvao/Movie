package com.claudiogalvaodev.moviemanager.data.webclient.dto.movie

import com.google.gson.annotations.SerializedName

data class BelongsToCollectionDto(
    val id: Int,
    val name: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("poster_path")
    val posterPath: String
)