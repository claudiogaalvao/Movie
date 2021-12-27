package com.claudiogalvaodev.moviemanager.data.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.claudiogalvaodev.moviemanager.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    // Flow do Coroutines. Parecido com o LiveData, quando o dado é atualizado, ele dispara essa atualização

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovies(movies: List<Movie>)
}