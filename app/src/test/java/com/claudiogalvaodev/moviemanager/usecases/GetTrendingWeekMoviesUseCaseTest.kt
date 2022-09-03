package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository
import com.claudiogalvaodev.moviemanager.usecases.movies.GetTrendingWeekMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetTrendingWeekMoviesUseCaseTest {

//    private lateinit var getTrendingWeekMoviesUseCase: GetTrendingWeekMoviesUseCase
//
//    @Mock
//    lateinit var repository: MoviesRepository
//
//    @Before
//    fun setUp() {
//        getTrendingWeekMoviesUseCase = GetTrendingWeekMoviesUseCase(repository)
//    }
//
//    @Test
//    fun shouldReturnJustMoviesWithPosterAndBackdropImage_WhenReceiveMoviesFromRemoteDatasource() = runBlocking {
//        Mockito.`when`(repository.getTrendingWeek()).thenReturn(Result.success(invalidMoviesTestList))
//
//        val moviesResult: List<MovieModel> = getTrendingWeekMoviesUseCase.invoke().getOrDefault(emptyList())
//        assertEquals(1, moviesResult.size)
//    }
//
//    @Test
//    fun shouldReturnTenMoviesOnList_WhenReceiveMoviesFromRemoteDatasource() = runBlocking {
//        Mockito.`when`(repository.getTrendingWeek()).thenReturn(Result.success(tenMoviesTestList))
//
//        val moviesResult = getTrendingWeekMoviesUseCase.invoke().getOrDefault(emptyList())
//        assertEquals(10, moviesResult.size)
//    }

}
//
//private val invalidMoviesTestList = listOf(
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", "teste", "teste", null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", null, null, null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", null, null, null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", null, null, null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//)

// 15 items where 3 is invalid movies
//private val tenMoviesTestList = listOf(
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", "teste", "teste", null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", null, null, null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", null, null, null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", null, null, null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", "teste", "teste", null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", "teste", "teste", null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", "teste", "teste", null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", "teste", "teste", null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", "teste", "teste", null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", "teste", "teste", null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", "teste", "teste", null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", "teste", "teste", null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", "teste", "teste", null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", "teste", "teste", null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//    MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//        "2022-02-01", 10.6, 5, 56.5, false, false,
//        "teste", "teste", "teste", null, 0,
//        0, emptyList(), emptyList(), "teste", 10),
//)