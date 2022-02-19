package com.claudiogalvaodev.moviemanager.data.bd.dao

import androidx.room.*
import com.claudiogalvaodev.moviemanager.data.bd.entity.MyList
import kotlinx.coroutines.flow.Flow

@Dao
interface MyListsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun populate(list: List<MyList>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(list: MyList): Long

    @Query("SELECT * FROM MyList")
    fun getAll(): Flow<List<MyList>>

    @Query("DELETE FROM MyList WHERE id = :myListId")
    fun delete(myListId: Int)

    @Query("UPDATE MyList SET posterPath = :posterPath WHERE id = :myListId")
    suspend fun updatePosterPath(myListId: Int, posterPath: String)

}