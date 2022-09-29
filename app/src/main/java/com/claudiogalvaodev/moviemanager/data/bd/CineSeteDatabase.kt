package com.claudiogalvaodev.moviemanager.data.bd

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.claudiogalvaodev.moviemanager.data.bd.converter.Converters
import com.claudiogalvaodev.moviemanager.data.bd.dao.MoviesSavedDao
import com.claudiogalvaodev.moviemanager.data.bd.dao.CustomListsDao
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSavedEntity
import com.claudiogalvaodev.moviemanager.data.bd.entity.CustomListEntity

@Database(
    entities = [CustomListEntity::class, MovieSavedEntity::class],
    version = 5,
    autoMigrations = [
        AutoMigration(from = 4, to = 5, spec = CineSeteDatabase.Migration4To5::class)
    ],
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
                .build()

            return instance
        }
    }

    @RenameTable(fromTableName = "MyList", toTableName = "CustomListEntity")
    @RenameTable(fromTableName = "MovieSaved", toTableName = "MovieSavedEntity")
    class Migration4To5 : AutoMigrationSpec
}