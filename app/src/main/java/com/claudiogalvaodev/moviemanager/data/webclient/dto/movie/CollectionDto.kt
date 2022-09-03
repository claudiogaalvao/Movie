package com.claudiogalvaodev.moviemanager.data.webclient.dto.movie

import com.claudiogalvaodev.moviemanager.ui.model.CollectionModel
import com.google.gson.annotations.SerializedName

data class CollectionDto(
    val id: Int,
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val parts: List<MovieDto>
)

fun CollectionDto.toModel(): CollectionModel = CollectionModel(
    id = this.id,
    name = this.name,
    posterPath = this.posterPath,
    backdropPath = this.backdropPath,
    parts = this.parts.toListOfMoviesModel()
)