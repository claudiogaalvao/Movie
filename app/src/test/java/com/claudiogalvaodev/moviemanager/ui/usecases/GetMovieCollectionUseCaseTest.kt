package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.model.Collection
import com.claudiogalvaodev.moviemanager.model.Movie
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetMovieCollectionUseCaseTest {

    private lateinit var getMovieCollectionUseCase: GetMovieCollectionUseCase

    @Mock
    lateinit var repository: MoviesRepository

    @Before
    fun setUp() {
        getMovieCollectionUseCase = GetMovieCollectionUseCase(repository)
    }

    @Test
    fun shouldNotReturnCollectionNull_WhenReceiveCollectionFromRemoteDatasource() = runBlocking {
        Mockito.`when`(repository.getCollection(Mockito.anyInt())).thenReturn(Result.success(collectionTest))

        val collectionResult = getMovieCollectionUseCase.invoke(1).getOrDefault(null)
        Assert.assertTrue(collectionResult != null)
    }

    @Test
    fun shouldReturnCollectionWithMovies_WhenReceiveCollectionFromRemoteDatasource() = runBlocking {
        Mockito.`when`(repository.getCollection(Mockito.anyInt())).thenReturn(Result.success(collectionTest))

        val collectionResult = getMovieCollectionUseCase.invoke(1).getOrDefault(null)
        if(collectionResult != null) {
            Assert.assertEquals(2, collectionResult.parts.size)
        }
    }

}

private val moviesListTest = listOf(
    Movie(1, "Primeiro filme", "Primeiro filme", "English",
        "2022-02-01", 10.6, 5, 56.5, false, false,
        "teste", "teste", "teste", null, 0,
        0, emptyList(), emptyList(), "teste", 10),
    Movie(1, "Primeiro filme", "Primeiro filme", "English",
        "2022-02-01", 10.6, 5, 56.5, false, false,
        "teste", "teste", "teste", null, 0,
        0, emptyList(), emptyList(), "teste", 10),
)

private val collectionTest = Collection(1, "Teste", "teste", "teste", moviesListTest)