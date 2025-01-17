package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.webclient.dto.movie.ProviderDto
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository
import com.claudiogalvaodev.moviemanager.usecases.movies.GetMovieProvidersUseCase
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
class GetMovieModelProvidersUseCaseTest {

    private lateinit var getMovieProvidersUseCase: GetMovieProvidersUseCase

    @Mock
    lateinit var repository: MoviesRepository

    @Before
    fun setUp() {
        getMovieProvidersUseCase = GetMovieProvidersUseCase(repository)
    }

//    @Test
//    fun shouldNotReturnProvidersNull_WhenReceiveProvidersListFromRemoteDatasource() = runBlocking {
//        Mockito.`when`(repository.getProviders(Mockito.anyInt())).thenReturn(Result.success(providersList))
//
//        val providersResult = getMovieProvidersUseCase.invoke(1).getOrDefault(emptyList())
//        Assert.assertTrue(providersResult != null)
//    }
//
//    @Test
//    fun shouldReturnProvidersList_WhenReceiveProvidersListFromRemoteDatasource() = runBlocking {
//        Mockito.`when`(repository.getProviders(Mockito.anyInt())).thenReturn(Result.success(providersList))
//
//        val providersResult = getMovieProvidersUseCase.invoke(1).getOrDefault(emptyList())
//        if(providersResult != null) {
//            Assert.assertEquals(3, providersResult.size)
//        }
//    }

}

private val providersList = listOf(
    ProviderDto(1, "teste", 1, "teste"),
    ProviderDto(2, "teste", 2, "teste"),
    ProviderDto(3, "teste", 3, "teste"),
)