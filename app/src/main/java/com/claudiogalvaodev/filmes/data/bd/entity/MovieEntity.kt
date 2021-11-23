package com.claudiogalvaodev.filmes.data.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// Considerar o uso de mais de uma entidade para evitar trazer informações que não serão usadas
// Entity Movie (id, title, genre_id, poster_path, isFavorite, summary: Summary)
// Data class Summary (original_title, original_language, release_date, popularity, vote_count, vote_average, overview, is_favorite)

// Ter uma tabela Favoritos, que armazena somente os ids
// Quando receber o result da API, pegar os dados da tabela Favoritos e lançar em uma lista
// Comparar os ids da lista com os ids dos filmes retornados da API
// Se o id do filme estiver na lista, mudar o isFavorite do filme para true

// Pesquisar sobre DTO
@Entity(tableName = "movies")
class MovieEntity(
    @PrimaryKey
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
    val backdrop_path: String,
    val poster_path: String,
): Serializable {

    fun getCoverage() : String {
        return "https://image.tmdb.org/t/p/w500$poster_path"
    }

    override fun toString(): String {
        return title
    }
}