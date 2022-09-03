package com.claudiogalvaodev.moviemanager.data.webclient.dto.movie

import com.claudiogalvaodev.moviemanager.data.webclient.dto.enum.MovieStatusDto
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.claudiogalvaodev.moviemanager.utils.extensions.toDate
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class MovieDto(
    val id: Int,
    val title: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String?,
    val runtime: Int?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val genres: List<GenreDto>?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollectionDto?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    val budget: Int?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    val popularity: Double?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountryDto>?,
    val revenue: Int?,
    val status: MovieStatusDto?,
    val tagline: String?,
    @SerializedName("vote_count")
    val voteCount: Int?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    val homepage: String?,
    @SerializedName("video")
    val hasVideo: Boolean?,
    @SerializedName("adult")
    val isAdult: Boolean?
)

fun MovieDto.toModel(): MovieModel = MovieModel(
    id = this.id,
    title = this.title ?: "",
    overview = this.overview,
    runtime = this.runtime,
    releaseDate = this.releaseDate,
    genres = this.genres?.map { genreDto -> genreDto.toModel() } ?: emptyList(),
    backdropPath = this.backdropPath ?: "",
    posterPath = this.posterPath ?: "",
    budget = this.budget ?: 0,
    voteAverage = this.voteAverage ?: 0.0,
    productionCompanies = this.productionCompanies?.toListOfProductionCompanyModel() ?: emptyList(),
    collectionId = this.belongsToCollection?.id
)

fun List<MovieDto>?.toListOfMoviesModel(): List<MovieModel> = this?.let { movies ->
    movies
        .filter { movieDto ->
            !movieDto.posterPath.isNullOrEmpty() && !movieDto.backdropPath.isNullOrEmpty()
        }
        .map { movieDto -> movieDto.toModel() }
} ?: emptyList()

fun List<MovieDto>.getJustUpComingMovies(): List<MovieDto> {
    val currentDate = LocalDate.now()
    return this.filter { movieModel ->
        movieModel.releaseDate?.toDate()?.isAfter(currentDate) ?: false
    }
}

fun List<MovieDto>.getJustPlayingNowMovies(): List<MovieDto> {
    val currentDate = LocalDate.now()
    return this.filter { movieModel ->
        movieModel.releaseDate?.toDate()?.let {
            it.isEqual(currentDate) || it.isBefore(currentDate)
        } ?: false
    }
}

// TODO Do I need to convert string to date?
fun List<MovieDto>.orderMoviesByAscendingReleaseDate(): List<MovieDto> {
    return this.sortedByDescending { movieDto ->
        movieDto.releaseDate?.toDate()
    }.reversed()
}

fun List<MovieDto>.orderMoviesByDescendingReleaseDate(): List<MovieDto> {
    return this.sortedByDescending { movieDto ->
        movieDto.releaseDate?.toDate()
    }
}