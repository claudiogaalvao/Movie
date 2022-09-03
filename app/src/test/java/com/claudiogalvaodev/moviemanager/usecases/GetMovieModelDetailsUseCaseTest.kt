package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository
import com.claudiogalvaodev.moviemanager.usecases.movies.GetMovieDetailsUseCase
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
class GetMovieModelDetailsUseCaseTest {

    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @Mock
    lateinit var repository: MoviesRepository

    @Before
    fun setUp() {
        getMovieDetailsUseCase = GetMovieDetailsUseCase(repository)
    }

//    @Test
//    fun shouldReturnMovie_WhenReceiveMovieDetailsFromRemoteDatasource() = runBlocking {
//        Mockito.`when`(repository.getDetails(Mockito.anyInt())).thenReturn(Result.success(movieDetailsTest))
//
//        getMovieDetailsUseCase.invoke(1)
//        getMovieDetailsUseCase.movie.test {
//            Assert.assertTrue(expectMostRecentItem() is MovieModel)
//        }
//    }
//
//    @Test
//    fun shouldReturnCompanies_WhenReceiveMovieDetailsWithCompaniesListNotEmptyFromRemoveDatasource() = runBlocking {
//        Mockito.`when`(repository.getDetails(Mockito.anyInt())).thenReturn(Result.success(movieDetailsTest))
//
//        getMovieDetailsUseCase.invoke(1)
//        getMovieDetailsUseCase.companies.test {
//            Assert.assertTrue(expectMostRecentItem() != null)
//        }
//    }
//
//    @Test
//    fun shouldReturnJustCompaniesWithPosterPath_WhenReceiveMovieDetailsWithCompaniesFromRemoveDatasource() = runBlocking {
//        Mockito.`when`(repository.getDetails(Mockito.anyInt())).thenReturn(Result.success(movieDetailsTest))
//
//        getMovieDetailsUseCase.invoke(1)
//        getMovieDetailsUseCase.companies.test {
//            Assert.assertEquals(2, expectMostRecentItem()?.size)
//        }
//    }

}

//private val companiesList = listOf(
//    Company(1, null, "teste", "teste"),
//    Company(2, "teste", "teste", "teste"),
//    Company(3, "teste", "teste", "teste"),
//)
//
//private val movieDetailsTest = MovieModel(1, "Primeiro filme", "Primeiro filme", "English",
//    "2022-02-01", 10.6, 5, 56.5, false, false,
//    "teste", "teste", "teste", null, 0,
//    0, emptyList(), companiesList, "teste", 10)
