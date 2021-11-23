package com.claudiogalvaodev.filmes.repository

import androidx.lifecycle.liveData
import com.claudiogalvaodev.filmes.data.bd.dao.MovieDao
import com.claudiogalvaodev.filmes.model.FavoriteMovieEntity
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.webclient.service.MovieService

sealed class Result<out R> {
    data class Success<out T>(val data: T?) : Result<T?>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class MoviesRepository(
    private val dao: MovieDao,
    private val service: MovieService
) {

    fun getPopularMovies() = liveData {
        try {
            val response = service.getPopularMovies()
            if(response.isSuccessful) {
                response.body()?.results?.let { movieList ->
                    dao.insertPopularMovies(movieList)
                    emit(Result.Success(movieList))
                }
            } else {
                emit(Result.Error(exception = Exception("Falha ao buscar o filme")))
            }
        } catch (e: Exception) {
            emit(Result.Error(exception = e))
        }
    }

    fun isFavorite(movieId: Int): Boolean {
        val favoriteMovie = dao.getFavoriteMovieById(movieId)
        return favoriteMovie != null
    }

    fun getFavoriteMovies() = liveData {
        val movies = dao.getPopularMovies()
        val favoriteMoviesId = dao.getFavoriteMovies()

        val favoriteMovies = if(movies?.isNotEmpty() && favoriteMoviesId?.isNotEmpty()) {
            getFavoriteMoviesFromListById(movies, favoriteMoviesId)
        } else {
            null
        }

        if (favoriteMovies != null) {
            emit(Result.Success(favoriteMovies))
        } else {
            emit(Result.Error(exception = Exception("Não existe favoritos para mostrar")))
        }
    }

    private fun getFavoriteMoviesFromListById(movies: List<MovieEntity>, favoriteMoviesId: List<FavoriteMovieEntity>): List<MovieEntity> {
        val favoriteMovies: MutableList<MovieEntity> = mutableListOf()
        movies.map { movie ->
            favoriteMoviesId.map { favoriteMovieId ->
                if(movie.id == favoriteMovieId.id) {
                    favoriteMovies.add(movie)
                }
            }
        }
        return favoriteMovies
    }

    suspend fun insertFavoriteMovie(movieId: Int) {
        val favoriteMovie = FavoriteMovieEntity(movieId)
        dao.insertFavoriteMovie(favoriteMovie)
    }

    suspend fun deleteFavoriteMovie(movie: FavoriteMovieEntity) {
        dao.deleteFavoriteMovie(movie)
    }
}