package com.claudiogalvaodev.moviemanager.data.bd.dao

import androidx.room.*
import com.claudiogalvaodev.moviemanager.data.bd.entity.MyList
import kotlinx.coroutines.flow.Flow

@Dao
interface MyListsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createNewList(list: MyList): Long

    @Query("SELECT * FROM MyList")
    fun getAllMyLists(): Flow<List<MyList>>

    @Query("DELETE FROM MyList WHERE id = :myListId")
    fun deleteList(myListId: Int)

}