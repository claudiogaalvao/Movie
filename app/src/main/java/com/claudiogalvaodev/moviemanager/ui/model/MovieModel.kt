package com.claudiogalvaodev.moviemanager.ui.model

import com.claudiogalvaodev.moviemanager.BuildConfig
import com.claudiogalvaodev.moviemanager.utils.enums.BackdropSizes
import com.claudiogalvaodev.moviemanager.utils.enums.PosterSizes
import com.claudiogalvaodev.moviemanager.utils.getBackdropSize
import com.claudiogalvaodev.moviemanager.utils.getPosterSize

data class MovieModel(
    val id: Int,
    val title: String,
    val overview: String?,
    val runtime: Int?,
    val releaseDate: String?,
    val genres: List<GenreModel>,
    val backdropPath: String,
    var posterPath: String,
    val budget: Int,
    val voteAverage: Double,
    val productionCompanies: List<ProductionCompanyModel>,
    val collectionId: Int?
) {

    fun getDuration(): String {
        return if (runtime != null && runtime > 0) {
            "${runtime / 60}h${runtime % 60}min"
        } else "NaN"
    }

    fun getGenresStringList(): List<String> {
        return genres.map { genre -> genre.name }
    }

    fun getVoteAverage(): String {
        return "${voteAverage}/10"
    }

    fun getPosterUrl(imageSize: PosterSizes = PosterSizes.W_500) : String {
        return "${BuildConfig.MOVIEDB_IMAGE_BASE_URL}${getPosterSize(imageSize)}$posterPath"
    }

    fun savePosterPathFromPosterUrl(posterUrl: String) {
        val imageSize: PosterSizes = PosterSizes.W_500
        posterPath = posterUrl.replace(
            "${BuildConfig.MOVIEDB_IMAGE_BASE_URL}${getPosterSize(imageSize)}",
            ""
        )
    }

    fun getBackdropUrl(imageSize: BackdropSizes = BackdropSizes.W_780) : String {
        return "${BuildConfig.MOVIEDB_IMAGE_BASE_URL}${getBackdropSize(imageSize)}$backdropPath"
    }

    override fun toString(): String {
        return title
    }
}