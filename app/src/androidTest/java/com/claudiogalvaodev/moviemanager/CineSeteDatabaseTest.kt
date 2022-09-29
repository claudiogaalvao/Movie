package com.claudiogalvaodev.moviemanager

import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.claudiogalvaodev.moviemanager.data.bd.migration.AppDatabaseMigrationHelper
import com.claudiogalvaodev.moviemanager.data.bd.CineSeteDatabase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CineSeteDatabaseTest {

    private val TEST_DB = "migration-test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        CineSeteDatabase::class.java,
    )

    @Test
    fun migrate1to2() {
        var db = helper.createDatabase(TEST_DB, 1)
        db.close()

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        db = helper.runMigrationsAndValidate(TEST_DB, 2, true)
        db.close()
    }

    @Test
    fun migrate2To3() {
        var db = helper.createDatabase(TEST_DB, 2)
        db.close()

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        db = helper.runMigrationsAndValidate(TEST_DB, 3, true, AppDatabaseMigrationHelper.MIGRATION_2_3)
        db.close()
        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.
    }

    @Test
    fun migrate3To4() {
        val db = helper.createDatabase(TEST_DB, 3)
        db.close()

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        helper.runMigrationsAndValidate(TEST_DB, 4, true, AppDatabaseMigrationHelper.MIGRATION_3_4)

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.
    }

//    @Test
//    fun migrate4To5() {
//        val db = helper.createDatabase(TEST_DB, 4)
//        db.close()
//
//        // Re-open the database with version 2 and provide
//        // MIGRATION_1_2 as the migration process.
//        helper.runMigrationsAndValidate(TEST_DB, 5, true)
//
//        // MigrationTestHelper automatically verifies the schema changes,
//        // but you need to validate that the data was migrated properly.
//    }
}