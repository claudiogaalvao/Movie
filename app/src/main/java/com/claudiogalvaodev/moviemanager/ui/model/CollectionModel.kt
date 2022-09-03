package com.claudiogalvaodev.moviemanager.ui.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class CollectionModel(
    val id: Int,
    val name: String,
    val posterPath: String?,
    val backdropPath: String?,
    val parts: List<MovieModel>
)

fun CollectionModel.sortCollectionByAscendingDate(): CollectionModel =
    run {
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val partsSorted = this.parts
            .filter { movie -> movie.releaseDate?.isNotBlank() ?: false }
            .sortedBy { movie ->
                LocalDate.parse(movie.releaseDate, dateTimeFormatter)
            }
        this.copy(
            parts = partsSorted
        )
    }
