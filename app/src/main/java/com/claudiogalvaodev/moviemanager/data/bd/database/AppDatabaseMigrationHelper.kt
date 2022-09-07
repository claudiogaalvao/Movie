package com.claudiogalvaodev.moviemanager.data.bd.database

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal object AppDatabaseMigrationHelper {

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE `OscarNomination` (`id` INTEGER NOT NULL,\n" +
                    "`itemId` INTEGER NOT NULL, `type` TEXT NOT NULL, `title` TEXT NOT NULL,\n" +
                    "`subtitle` TEXT, `imageUrl` TEXT NOT NULL,`releaseDate` TEXT,\n" +
                    "`leastOneMovieId` INTEGER, `categories` TEXT NOT NULL, `categoriesWinner` TEXT NOT NULL,\n" +
                    "PRIMARY KEY(`id` AUTOINCREMENT))")
        }
    }

    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("DROP TABLE OscarNomination")
        }
    }

//    val MIGRATION_4_5 = object : Migration(4, 5) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            try {
//                // CustomListEntity
//                database.execSQL("CREATE TABLE IF NOT EXISTS `CustomListEntity` (id INTEGER NOT NULL, name TEXT NOT NULL, posterPath TEXT, PRIMARY KEY(`id`))")
//                database.execSQL("INSERT INTO CustomListEntity (id, name, posterPath) SELECT id, name, posterPath FROM MyList")
//                database.execSQL("DROP TABLE MyList")
//
//                // MovieSavedEntity
//                database.execSQL("CREATE TABLE IF NOT EXISTS `MovieSavedEntity` (id INTEGER NOT NULL, movieId INTEGER NOT NULL, posterPath TEXT, listId INTEGER NOT NULL, PRIMARY KEY(`id`))")
//                database.execSQL("INSERT INTO MovieSavedEntity (id, movieId, posterPath, listId) SELECT id, movieId, moviePosterUrl, myListId FROM MovieSaved")
//                database.execSQL("DROP TABLE MovieSaved")
//            } catch (e: SQLiteException) {
//                Log.i("migration", e.message.toString())
//            }
//        }
//    }

}