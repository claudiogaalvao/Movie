package com.claudiogalvaodev.filmes.data.bd.dao

import androidx.room.*
import com.claudiogalvaodev.filmes.data.bd.entity.MovieEntity
import com.claudiogalvaodev.filmes.model.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    // Flow do Coroutines. Parecido com o LiveData, quando o dado é atualizado, ele dispara essa atualização

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    fun getPopularMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(favorite: FavoriteMovieEntity)

    @Query("SELECT movies.* FROM movies JOIN favorites ON movies.id = favorites.id")
    fun getMoviesAndFavorites(): Flow<List<MovieEntity>?>

    @Query("SELECT * FROM favorites WHERE id = :id")
    fun getFavoriteMovieById(id: Int): Flow<FavoriteMovieEntity?>

    @Delete
    suspend fun deleteFavoriteMovie(favorite: FavoriteMovieEntity)
}