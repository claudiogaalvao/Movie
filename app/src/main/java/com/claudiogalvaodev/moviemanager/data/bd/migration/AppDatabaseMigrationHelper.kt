package com.claudiogalvaodev.moviemanager.data.bd.migration

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal object AppDatabaseMigrationHelper {

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("CREATE TABLE `OscarNomination` (`id` INTEGER NOT NULL,\n" +
                    "`itemId` INTEGER NOT NULL, `type` TEXT NOT NULL, `title` TEXT NOT NULL,\n" +
                    "`subtitle` TEXT, `imageUrl` TEXT NOT NULL,`releaseDate` TEXT,\n" +
                    "`leastOneMovieId` INTEGER, `categories` TEXT NOT NULL, `categoriesWinner` TEXT NOT NULL,\n" +
                    "PRIMARY KEY(`id` AUTOINCREMENT))")
        }
    }

    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("DROP TABLE OscarNomination")
        }
    }

    val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(db: SupportSQLiteDatabase) {
            try {
                // CustomListEntity
                db.execSQL("ALTER TABLE MyList RENAME TO CustomListEntity")

                // MovieSavedEntity
                db.execSQL("ALTER TABLE MovieSaved RENAME TO MovieSavedEntity")
                db.execSQL("ALTER TABLE MovieSavedEntity RENAME COLUMN moviePosterUrl TO posterPath")
                db.execSQL("ALTER TABLE MovieSavedEntity RENAME COLUMN myListId TO listId")
            } catch (e: SQLiteException) {
                Log.i("migration", e.message.toString())
            }
        }
    }

}