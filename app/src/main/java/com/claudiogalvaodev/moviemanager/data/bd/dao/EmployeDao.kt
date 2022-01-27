package com.claudiogalvaodev.moviemanager.data.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.claudiogalvaodev.moviemanager.model.Employe
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeDao {

    @Query("SELECT * FROM Employe")
    fun getEmployeSelected(): Flow<List<Employe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployeSelected(people: List<Employe>)

    @Query("DELETE FROM Employe WHERE id = :id")
    suspend fun removePersonSelected(id: Long)
}