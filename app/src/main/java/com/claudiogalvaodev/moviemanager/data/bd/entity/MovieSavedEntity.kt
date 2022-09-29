package com.claudiogalvaodev.moviemanager.data.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel

@Entity
data class MovieSavedEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val movieId: Int,
    val moviePosterUrl: String,
    val myListId: Int
)

fun MovieSavedEntity.toModel() = MovieModel(
    id = this.movieId,
    title = "",
    overview = "",
    runtime = 0,
    releaseDate = "",
    genres = emptyList(),
    backdropPath = "",
    posterPath = this.moviePosterUrl,
    budget = 0,
    voteAverage = 0.0,
    collectionId = null,
    productionCompanies = emptyList()
)

fun List<MovieSavedEntity>.toListOfMovieModel() = this.map { it.toModel() }