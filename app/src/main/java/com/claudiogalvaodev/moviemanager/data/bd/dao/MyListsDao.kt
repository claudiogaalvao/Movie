package com.claudiogalvaodev.moviemanager.data.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.claudiogalvaodev.moviemanager.data.bd.entity.MyList
import kotlinx.coroutines.flow.Flow

@Dao
interface MyListsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createNewList(list: MyList): Long

    @Query("SELECT * FROM MyList")
    fun getAllMyLists(): Flow<List<MyList>>

}