package com.claudiogalvaodev.moviemanager.usecases

import app.cash.turbine.test
import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDate

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetUpComingAndPlayingNowMoviesUseCaseTest {

    private lateinit var getUpComingAndPlayingNowMoviesUseCase: GetUpComingAndPlayingNowMoviesUseCase

    @Mock
    lateinit var repository: MoviesRepository

    @Before
    fun setUp() {
        getUpComingAndPlayingNowMoviesUseCase = GetUpComingAndPlayingNowMoviesUseCase(repository)
    }

    @Test
    fun shouldReturnJustUpcomingMoviesWithPosterAndBackdropImage_WhenReceiveMoviesFromRemoteDatasource() = runBlocking {
        val futureDate = (LocalDate.now().plusDays(10)).toString()
        Mockito.`when`(repository.getUpComing()).thenReturn(Result.success(moviesTestList(futureDate)))
        Mockito.`when`(repository.getPlayingNow()).thenReturn(Result.success(emptyList()))

        getUpComingAndPlayingNowMoviesUseCase.invoke()
        getUpComingAndPlayingNowMoviesUseCase.upComingMovies.test {
            Assert.assertEquals(1, expectMostRecentItem().size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldReturnJustPlayingNowMoviesWithPosterAndBackdropImage_WhenReceiveMoviesFromRemoteDatasource() = runBlocking {
        val pastDate = (LocalDate.now().minusDays(10)).toString()
        Mockito.`when`(repository.getPlayingNow()).thenReturn(Result.success(moviesTestList(pastDate)))
        Mockito.`when`(repository.getUpComing()).thenReturn(Result.success(emptyList()))

        getUpComingAndPlayingNowMoviesUseCase.invoke()
        getUpComingAndPlayingNowMoviesUseCase.playingNowMovies.test {
            Assert.assertEquals(1, expectMostRecentItem().size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldReturnJustUpComingMoviesWithDateAfterCurrentDate_WhenReceiveMoviesFromRemoveDatasource() = runBlocking {
        val pastDate = (LocalDate.now().minusDays(10)).toString()
        val futureDate = (LocalDate.now().plusDays(10)).toString()
        Mockito.`when`(repository.getPlayingNow()).thenReturn(Result.success(emptyList()))
        Mockito.`when`(repository.getUpComing()).thenReturn(Result.success(
            dynamicScenariosForUpcomingAndPlayingNowTestList(
                pastDate = pastDate, futureDate = futureDate,
                countPastScenarios = 2, countFutureScenarios = 5)))

        getUpComingAndPlayingNowMoviesUseCase.invoke()
        getUpComingAndPlayingNowMoviesUseCase.upComingMovies.test {
            Assert.assertEquals(5, expectMostRecentItem().size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldReturnJustPlayingNowMoviesWithDateEqualOrBeforeCurrentDate_WhenReceiveMoviesFromRemoveDatasource() = runBlocking {
        val pastDate = (LocalDate.now().minusDays(10)).toString()
        val futureDate = (LocalDate.now().plusDays(10)).toString()
        Mockito.`when`(repository.getPlayingNow()).thenReturn(Result.success(
            dynamicScenariosForUpcomingAndPlayingNowTestList(
                pastDate = pastDate, futureDate = futureDate,
                countPastScenarios = 5, countFutureScenarios = 2)))
        Mockito.`when`(repository.getUpComing()).thenReturn(Result.success(emptyList()))

        getUpComingAndPlayingNowMoviesUseCase.invoke()
        getUpComingAndPlayingNowMoviesUseCase.playingNowMovies.test {
            Assert.assertEquals(5, expectMostRecentItem().size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldAttributePlayingNowMoviesFromUpcomingToPlayingNowMovies_WhenReceiveBothTypesInUpcomingMovies() = runBlocking {
        val pastDate = (LocalDate.now().minusDays(10)).toString()
        val futureDate = (LocalDate.now().plusDays(10)).toString()
        Mockito.`when`(repository.getPlayingNow()).thenReturn(Result.success(emptyList()))
        Mockito.`when`(repository.getUpComing()).thenReturn(Result.success(
            dynamicScenariosForUpcomingAndPlayingNowTestList(
                pastDate = pastDate, futureDate = futureDate,
                countPastScenarios = 2, countFutureScenarios = 5)))

        getUpComingAndPlayingNowMoviesUseCase.invoke()
        getUpComingAndPlayingNowMoviesUseCase.playingNowMovies.test {
            Assert.assertEquals(2, expectMostRecentItem().size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldAttributeUpcomingMoviesFromPlayingNowToUpcomingMovies_WhenReceiveBothTypesInPlayingNowMovies() = runBlocking {
        val pastDate = (LocalDate.now().minusDays(10)).toString()
        val futureDate = (LocalDate.now().plusDays(10)).toString()
        Mockito.`when`(repository.getPlayingNow()).thenReturn(Result.success(
            dynamicScenariosForUpcomingAndPlayingNowTestList(
                pastDate = pastDate, futureDate = futureDate,
                countPastScenarios = 5, countFutureScenarios = 2)))
        Mockito.`when`(repository.getUpComing()).thenReturn(Result.success(emptyList()))

        getUpComingAndPlayingNowMoviesUseCase.invoke()
        getUpComingAndPlayingNowMoviesUseCase.upComingMovies.test {
            Assert.assertEquals(2, expectMostRecentItem().size)
            cancelAndIgnoreRemainingEvents()
        }
    }
}

private fun moviesTestList(date: String): List<Movie> {
    return listOf(
        Movie(1, "Primeiro filme", "Primeiro filme", "English",
            date, 10.6, 5, 56.5, false, false,
            "teste", "teste", "teste", null, 0,
            0, emptyList(), emptyList(), "teste", 10),
        Movie(1, "Primeiro filme", "Primeiro filme", "English",
            date, 10.6, 5, 56.5, false, false,
            "teste", null, null, null, 0,
            0, emptyList(), emptyList(), "teste", 10),
        Movie(1, "Primeiro filme", "Primeiro filme", "English",
            date, 10.6, 5, 56.5, false, false,
            "teste", null, null, null, 0,
            0, emptyList(), emptyList(), "teste", 10),
        Movie(1, "Primeiro filme", "Primeiro filme", "English",
            date, 10.6, 5, 56.5, false, false,
            "teste", null, null, null, 0,
            0, emptyList(), emptyList(), "teste", 10),
    )
}

private fun dynamicScenariosForUpcomingAndPlayingNowTestList(
    pastDate: String, futureDate: String,
    countPastScenarios: Int, countFutureScenarios: Int): List<Movie> {
    val movies = mutableListOf<Movie>()

    for(countFuture in 1..countFutureScenarios) {
        movies.add(
            Movie(1, "Primeiro filme", "Primeiro filme", "English",
                futureDate, 10.6, 5, 56.5, false, false,
                "teste", "teste", "teste", null, 0,
                0, emptyList(), emptyList(), "teste", 10)
        )
    }

    for(countPast in 1..countPastScenarios) {
        movies.add(
            Movie(1, "Primeiro filme", "Primeiro filme", "English",
                pastDate, 10.6, 5, 56.5, false, false,
                "teste", "teste", "teste", null, 0,
                0, emptyList(), emptyList(), "teste", 10)
        )
    }

    return movies
}