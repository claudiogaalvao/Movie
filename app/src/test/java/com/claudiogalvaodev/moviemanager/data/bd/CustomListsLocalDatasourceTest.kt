package com.claudiogalvaodev.moviemanager.data.bd

import com.claudiogalvaodev.moviemanager.data.BaseTest
import com.claudiogalvaodev.moviemanager.data.bd.dao.CustomListsDao
import com.claudiogalvaodev.moviemanager.data.bd.dao.MoviesSavedDao
import com.claudiogalvaodev.moviemanager.data.bd.datasource.ICustomListsLocalDatasource
import com.claudiogalvaodev.moviemanager.data.bd.entity.CustomListEntity
import com.claudiogalvaodev.moviemanager.data.bd.entity.MovieSavedEntity
import com.claudiogalvaodev.moviemanager.ui.model.CustomListModel
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CustomListsLocalDatasourceTest : BaseTest() {

    private val customListsLocalDatasource: ICustomListsLocalDatasource by inject()

    lateinit var customListDao: CustomListsDao

    lateinit var moviesSavedDao: MoviesSavedDao

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        customListDao = declareMock()
        moviesSavedDao = declareMock()
    }

    @After
    fun setDown() {
        MockitoAnnotations.openMocks(this).close()
    }

    @Test
    fun `should return all custom list with movies saved`() = runBlocking {
        // Given
        Mockito.`when`(customListDao.getAll()).thenReturn(mockCustomListEntity)
        Mockito.`when`(moviesSavedDao.getAll()).thenReturn(mockMoviesSavedEntity)

        // When
        val customListsResult = customListsLocalDatasource.getAllCustomList()

        // Then
        val expected = listOf(
            CustomListModel(id = 1, name = "Favoritos", posterPath = "myImage", movies = listOf(
                mockMovieModel.copy(id = 100), mockMovieModel.copy(id = 200)
            )),
            CustomListModel(id = 2, name = "Quero Assistir", posterPath = "myImage", movies = listOf(
                mockMovieModel.copy(id = 300), mockMovieModel.copy(id = 400)
            )),
        )
        Assert.assertEquals(customListsResult.getOrNull(), expected)
    }

    @Test
    fun `should return all custom list with movies empty`() = runBlocking {
        // Given
        Mockito.`when`(customListDao.getAll()).thenReturn(mockCustomListEntity)
        Mockito.`when`(moviesSavedDao.getAll()).thenReturn(emptyList())

        // When
        val customListsResult = customListsLocalDatasource.getAllCustomList()

        // Then
        val expected = listOf(
            CustomListModel(id = 1, name = "Favoritos", posterPath = "myImage", movies = emptyList()),
            CustomListModel(id = 2, name = "Quero Assistir", posterPath = "myImage", movies = emptyList()),
        )
        Assert.assertEquals(customListsResult.getOrNull(), expected)
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

private val mockMovieModel = MovieModel(
    id = 0,
    title = "",
    overview = "",
    runtime = 0,
    releaseDate = "",
    genres = emptyList(),
    backdropPath = "",
    posterPath = "myImage",
    budget = 0,
    voteAverage = 0.0,
    collectionId = null,
    productionCompanies = emptyList()
)