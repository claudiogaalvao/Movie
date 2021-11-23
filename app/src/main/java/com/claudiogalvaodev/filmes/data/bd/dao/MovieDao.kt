package com.claudiogalvaodev.filmes.data.bd.dao

import androidx.room.*
import com.claudiogalvaodev.filmes.model.FavoriteMovieEntity
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPopularMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    fun getPopularMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(favoriteMovie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorites")
    fun getFavoriteMovies(): List<FavoriteMovieEntity>

    @Query("SELECT * FROM favorites WHERE id = :id")
    fun getFavoriteMovieById(id: Int): FavoriteMovieEntity?

    @Delete
    fun deleteFavoriteMovie(favoriteMovie: FavoriteMovieEntity)
}