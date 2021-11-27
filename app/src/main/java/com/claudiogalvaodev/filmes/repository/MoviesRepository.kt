package com.claudiogalvaodev.filmes.repository

import com.claudiogalvaodev.filmes.data.bd.dao.MovieDao
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.model.FavoriteMovieEntity
import com.claudiogalvaodev.filmes.webclient.service.MovieService

class MoviesRepository(
    private val dao: MovieDao,
    private val service: MovieService
) {

    suspend fun getPopularMovies(): Result<List<MovieEntity>> {
        var result: Result<List<MovieEntity>> = Result.success(emptyList())
        try {
            val response = service.getPopularMovies()
            if(response.isSuccessful) {
                response.body()?.results?.let { movieList ->
                    dao.insertPopularMovies(movieList)
                    result = Result.success(movieList)
                }
            } else {
                result = Result.failure(exception = Exception("Falha ao buscar o filme"))
            }
        } catch (e: Exception) {
            result = Result.failure(exception = e)
        }
        return result
    }

    fun getFavoriteMovieById(movieId: Int) = dao.getFavoriteMovieById(movieId)

    fun getFavoriteMovies() = dao.getMoviesAndFavorites()

    suspend fun insertFavoriteMovie(movieId: Int) {
        val favoriteMovie = FavoriteMovieEntity(movieId)
        dao.insertFavoriteMovie(favoriteMovie)
    }

    suspend fun deleteFavoriteMovie(movie: FavoriteMovieEntity) {
        dao.deleteFavoriteMovie(movie)
    }
}