package com.claudiogalvaodev.moviemanager.data.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.claudiogalvaodev.moviemanager.data.bd.converter.Converters
import com.claudiogalvaodev.moviemanager.data.bd.dao.MoviesSavedDao
import com.claudiogalvaodev.moviemanager.data.bd.dao.MyListsDao
import com.claudiogalvaodev.moviemanager.data.bd.dao.OscarNominationsDao
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSaved
import com.claudiogalvaodev.moviemanager.data.bd.entity.MyList
import com.claudiogalvaodev.moviemanager.data.bd.entity.OscarNomination

@Database(entities = [MyList::class, MovieSaved::class, OscarNomination::class], version = 2, exportSchema = true)
@TypeConverters(Converters::class)
abstract class CineSeteDatabase: RoomDatabase() {

    abstract val myListsDao: MyListsDao
    abstract val moviesSavedDao: MoviesSavedDao
    abstract val oscarNominationsDao: OscarNominationsDao

    companion object {
        private lateinit var instance: CineSeteDatabase

        fun getInstance(
            context: Context
        ) : CineSeteDatabase {
            if(Companion::instance.isInitialized) return instance

            instance = Room.databaseBuilder(context,
                CineSeteDatabase::class.java,
                "database")
                .createFromAsset("database/CineSete.db")
                .build()

            return instance
        }
    }
}