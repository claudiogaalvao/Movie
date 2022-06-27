package com.claudiogalvaodev.moviemanager.data.bd.dao

import androidx.room.*
import com.claudiogalvaodev.moviemanager.data.bd.entity.UserListEntity
import com.claudiogalvaodev.moviemanager.data.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface UserListsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(listEntity: UserListEntity): Long

    @Query("SELECT * FROM UserListEntity")
    fun getAll(): Flow<List<UserListEntity>>

    @Query("SELECT * FROM UserListEntity WHERE id = :userListId")
    fun getUserListById(userListId: Int): UserListEntity

    @Query("UPDATE UserListEntity SET movies = :movies WHERE id = :userListId")
    fun saveMovieOnUserList(userListId: Int, movies: List<Movie>)

    @Query("DELETE FROM UserListEntity WHERE id = :userListId")
    fun delete(userListId: Int)

}