package com.claudiogalvaodev.moviemanager.ui.usecases

import app.cash.turbine.test
import com.claudiogalvaodev.moviemanager.data.model.Credits
import com.claudiogalvaodev.moviemanager.data.model.Employe
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetMovieCreditsUseCaseTest {

    private lateinit var getMovieCreditsUseCase: GetMovieCreditsUseCase

    @Mock
    lateinit var repository: MoviesRepository

    @Before
    fun setUp() {
        getMovieCreditsUseCase = GetMovieCreditsUseCase(repository)
    }

    // should filter starts
    // should filter directors
    @Test
    fun shouldFilterStars_WhenReceiveCreditsFromRemoteDatasource() = runBlocking {
        Mockito.`when`(repository.getCredits(Mockito.anyInt())).thenReturn(
            Result.success(Credits(startsAndDirectorsListTest, startsAndDirectorsListTest)))

        getMovieCreditsUseCase.invoke(1)
        getMovieCreditsUseCase.stars.test {
            Assert.assertEquals(4, expectMostRecentItem()?.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldReturnDirectorsFromCastAndCrew_WhenReceiveCreditsFromRemoteDatasource() = runBlocking {
        Mockito.`when`(repository.getCredits(Mockito.anyInt())).thenReturn(
            Result.success(Credits(directorsSecondListTest, directorsThirdListTest)))

        getMovieCreditsUseCase.invoke(1)
        getMovieCreditsUseCase.directors.test {
            Assert.assertEquals(4, expectMostRecentItem()?.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldNotReturnDuplicatedDirectors_WhenReceiveCreditsFromRemoteDatasource() = runBlocking {
        Mockito.`when`(repository.getCredits(Mockito.anyInt())).thenReturn(
            Result.success(Credits(directorsFirstListTest, directorsSecondListTest)))

        getMovieCreditsUseCase.invoke(1)
        getMovieCreditsUseCase.directors.test {
            Assert.assertEquals(3, expectMostRecentItem()?.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

}

private val startsAndDirectorsListTest = listOf(
    Employe(true, 2, 1, "Acting", "Teste", "Teste", 2.0, "teste", 1, "Teste", "1", 1),
    Employe(true, 2, 1, "Acting", "Teste", "Teste", 2.0, "teste", 1, "Teste", "1", 1),
    Employe(true, 2, 1, "Acting", "Teste", "Teste", 2.0, "teste", 1, "Teste", "1", 1),
    Employe(true, 2, 1, "Acting", "Teste", "Teste", 2.0, "teste", 1, "Teste", "1", 1),
    Employe(true, 2, 1, "Directing", "Teste1", "Teste", 2.0, "teste", 1, "Teste", "1", 1),
    Employe(true, 2, 1, "Directing", "Teste2", "Teste", 2.0, "teste", 1, "Teste", "1", 1),
)

private val directorsFirstListTest = listOf(
    Employe(true, 2, 1, "Directing", "Teste1", "Teste1", 2.0, "teste1", 1, "Teste", "1", 1),
    Employe(true, 2, 3, "Directing", "Teste2", "Teste2", 2.0, "teste2", 1, "Teste", "1", 1),
)

private val directorsSecondListTest = listOf(
    Employe(true, 2, 4, "Directing", "Teste3", "Teste3", 2.0, "teste3", 1, "Teste", "1", 1),
    Employe(true, 2, 1, "Directing", "Teste1", "Teste1", 2.0, "teste1", 1, "Teste", "1", 1),
)

private val directorsThirdListTest = listOf(
    Employe(true, 2, 3, "Directing", "Teste3", "Teste3", 2.0, "teste3", 1, "Teste", "1", 1),
    Employe(true, 2, 4, "Directing", "Teste4", "Teste4", 2.0, "teste4", 1, "Teste", "1", 1),
)