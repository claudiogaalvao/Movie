package com.claudiogalvaodev.moviemanager.data.bd.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.claudiogalvaodev.moviemanager.data.bd.CineSeteDatabase
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSavedEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MoviesSavedDaoTest {

    private lateinit var database: CineSeteDatabase
    private lateinit var dao: MoviesSavedDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CineSeteDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.moviesSavedDao
    }

    @After
    fun tearDown() {
        database.close()
    }

}