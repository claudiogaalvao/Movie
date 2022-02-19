package com.claudiogalvaodev.moviemanager.data.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.claudiogalvaodev.moviemanager.data.bd.callback.MyListsDatabaseCallback
import com.claudiogalvaodev.moviemanager.data.bd.dao.MoviesSavedDao
import com.claudiogalvaodev.moviemanager.data.bd.dao.MyListsDao
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSaved
import com.claudiogalvaodev.moviemanager.data.bd.entity.MyList
import kotlinx.coroutines.CoroutineScope

@Database(entities = [MyList::class, MovieSaved::class], version = 1, exportSchema = false)
abstract class CineSeteDatabase: RoomDatabase() {

    abstract val myListsDao: MyListsDao
    abstract val moviesSavedDao: MoviesSavedDao

    companion object {
        private lateinit var instance: CineSeteDatabase

        fun getInstance(
            context: Context,
            coroutineScope: CoroutineScope? = null
        ) : CineSeteDatabase {
            if(Companion::instance.isInitialized) return instance

            instance = Room.databaseBuilder(context,
                CineSeteDatabase::class.java,
                "database")
                .addCallback(MyListsDatabaseCallback(context, coroutineScope, context.resources))
                .build()

            return instance
        }
    }
}