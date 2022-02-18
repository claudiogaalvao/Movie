package com.claudiogalvaodev.moviemanager.data.model

import android.os.Parcel
import android.os.Parcelable
import com.claudiogalvaodev.moviemanager.BuildConfig
import com.claudiogalvaodev.moviemanager.utils.enums.BackdropSizes
import com.claudiogalvaodev.moviemanager.utils.enums.PosterSizes
import kotlinx.parcelize.Parcelize

// Considerar o uso de mais de uma entidade para evitar trazer informações que não serão usadas
// Entity Movie (id, title, genre_id, poster_path, isFavorite, summary: Summary)
// Data class Summary (original_title, original_language, release_date, popularity, vote_count, vote_average, overview, is_favorite)

// Ter uma tabela Favoritos, que armazena somente os ids
// Quando receber o result da API, pegar os dados da tabela Favoritos e lançar em uma lista
// Comparar os ids da lista com os ids dos filmes retornados da API
// Se o id do filme estiver na lista, mudar o isFavorite do filme para true

// Pesquisar sobre DTO
class Movie(
    val id: Int,
    val title: String,
    val original_title: String,
    val original_language: String,
    val release_date: String,
    val popularity: Double,
    val vote_count: Int,
    val vote_average: Double,
    val adult: Boolean,
    val video: Boolean,
    val overview: String,
    val backdrop_path: String?,
    val poster_path: String?,
    val belongs_to_collection: Collection?,
    val budget: Long,
    val revenue: Long,
    val genres: List<Genre>,
    val production_companies: List<Company>,
    val status: String,
    val runtime: Int
) {

    fun getDuration(): String {
        return "${runtime / 60}h${runtime % 60}min"
    }

    fun getGenresStringList(): List<String> {
        return genres.map { genre -> genre.name }
    }

    fun getPoster(imageSize: PosterSizes = PosterSizes.W_500) : String {
        return "${BuildConfig.MOVIEDB_IMAGE_BASE_URL}${getPosterSize(imageSize)}$poster_path"
    }

    fun getBackdrop(imageSize: BackdropSizes = BackdropSizes.W_780) : String {
        return "${BuildConfig.MOVIEDB_IMAGE_BASE_URL}${getBackdropSize(imageSize)}$backdrop_path"
    }

    private fun getPosterSize(size: PosterSizes): String {
        return when(size) {
            PosterSizes.W_780 -> "w780"
            PosterSizes.W_500 -> "w500"
            PosterSizes.W_342 -> "w342"
            PosterSizes.W_185 -> "w185"
            PosterSizes.W_154 -> "w154"
            PosterSizes.W_92 -> "w92"
            else -> "original"
        }
    }

    private fun getBackdropSize(size: BackdropSizes): String {
        return when(size) {
            BackdropSizes.W_1280 -> "w1280"
            BackdropSizes.W_780 -> "w780"
            BackdropSizes.W_300 -> "w300"
            else -> "original"
        }
    }

    override fun toString(): String {
        return title
    }
}