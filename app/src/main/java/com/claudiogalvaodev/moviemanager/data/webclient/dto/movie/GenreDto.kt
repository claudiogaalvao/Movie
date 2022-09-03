package com.claudiogalvaodev.moviemanager.data.webclient.dto.movie

import com.claudiogalvaodev.moviemanager.ui.model.GenreModel

data class GenreDto(
    val id: Int,
    val name: String
)

fun GenreDto.toModel(): GenreModel = GenreModel(
    id = this.id,
    name = this.name
)

fun List<GenreDto>?.toListOfGenreModel(): List<GenreModel> = this?.let {
    it.map { genreDto -> genreDto.toModel() }
} ?: emptyList()