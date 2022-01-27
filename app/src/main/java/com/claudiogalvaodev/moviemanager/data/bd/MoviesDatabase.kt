package com.claudiogalvaodev.moviemanager.data.bd

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class MoviesDatabase: RoomDatabase() {

    companion object {
        private lateinit var db: MoviesDatabase

        fun getInstance(context: Context) : MoviesDatabase {
            if(Companion::db.isInitialized) return db

            db = Room.databaseBuilder(context,
                MoviesDatabase::class.java,
                "movies")
                .build()

            return db
        }
    }
}