package com.claudiogalvaodev.moviemanager.data.bd.datasource

import app.cash.turbine.test
import com.claudiogalvaodev.moviemanager.data.BaseTest
import com.claudiogalvaodev.moviemanager.data.bd.dao.CustomListsDao
import com.claudiogalvaodev.moviemanager.data.bd.dao.MoviesSavedDao
import com.claudiogalvaodev.moviemanager.data.bd.datasource.ICustomListsLocalDatasource
import com.claudiogalvaodev.moviemanager.data.bd.entity.*
import com.claudiogalvaodev.moviemanager.ui.model.CustomListModel
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CustomListsLocalDatasourceTest : BaseTest() {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var customListsLocalDatasource: ICustomListsLocalDatasource

    lateinit var customListDao: CustomListsDao

    lateinit var moviesSavedDao: MoviesSavedDao

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        customListDao = declareMock()
        moviesSavedDao = declareMock()
        Dispatchers.setMain(testDispatcher)
        customListsLocalDatasource = CustomListsLocalDatasource(
            customListsDao = customListDao,
            moviesSavedDao = moviesSavedDao,
            dispatcher = testDispatcher
        )
    }

    @After
    fun setDown() {
        MockitoAnnotations.openMocks(this).close()
        Dispatchers.resetMain()
    }

    @Test
    fun `should return all custom list with movies saved`() = runTest {
        // Given
        Mockito.`when`(customListDao.getAll()).thenReturn(flowOf(mockCustomListEntity))
        Mockito.`when`(moviesSavedDao.getAll()).thenReturn(flowOf(mockMoviesSavedEntity))

        // When - Then
        val expected = Result.success(mockCustomListModel)
        customListsLocalDatasource.getAllCustomList().test {
            assertEquals(expected, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

}

private val mockCustomListEntity = listOf(
    CustomListEntity(id = 1, "Favoritos", "myImage"),
    CustomListEntity(id = 2, "Quero Assistir", "myImage"),
)

private val mockMoviesSavedEntity = listOf(
    MovieSavedEntity(id = 1, 100, "myImage", 1),
    MovieSavedEntity(id = 2, 200, "myImage", 1),
    MovieSavedEntity(id = 3, 300, "myImage", 2),
    MovieSavedEntity(id = 4, 400, "myImage", 2),
)

private val mockCustomListModel = mockCustomListEntity.toListOfCustomListModel().map { customListModel ->
    customListModel.copy(movies = mockMoviesSavedEntity
        .filterMoviesByListId(customListModel.id)
        .toListOfMovieModel()
    )
}