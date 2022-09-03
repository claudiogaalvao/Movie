package com.claudiogalvaodev.moviemanager.data.bd.database

import android.content.Context
import androidx.room.*
import com.claudiogalvaodev.moviemanager.data.bd.converter.Converters
import com.claudiogalvaodev.moviemanager.data.bd.dao.MoviesSavedDao
import com.claudiogalvaodev.moviemanager.data.bd.dao.CustomListsDao
import com.claudiogalvaodev.moviemanager.data.bd.database.AppDatabaseMigrationHelper.MIGRATION_2_3
import com.claudiogalvaodev.moviemanager.data.bd.database.AppDatabaseMigrationHelper.MIGRATION_3_4
import com.claudiogalvaodev.moviemanager.data.bd.database.AppDatabaseMigrationHelper.MIGRATION_4_5
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSavedEntity
import com.claudiogalvaodev.moviemanager.data.bd.entity.CustomListEntity

@Database(
    entities = [CustomListEntity::class, MovieSavedEntity::class],
    version = 5,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class CineSeteDatabase: RoomDatabase() {

    abstract val customListsDao: CustomListsDao
    abstract val moviesSavedDao: MoviesSavedDao

    companion object {
        private lateinit var instance: CineSeteDatabase

        fun getInstance(
            context: Context,
        ) : CineSeteDatabase {
            if(Companion::instance.isInitialized) return instance

            instance = Room.databaseBuilder(context,
                CineSeteDatabase::class.java,
                "database")
                .addMigrations(
                    MIGRATION_2_3,
                    MIGRATION_3_4,
                    MIGRATION_4_5
                )
                .build()

            return instance
        }
    }
}