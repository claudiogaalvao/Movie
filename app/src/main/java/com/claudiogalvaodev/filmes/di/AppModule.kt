package com.claudiogalvaodev.filmes.di

import com.claudiogalvaodev.filmes.data.bd.MoviesDatabase
import com.claudiogalvaodev.filmes.repository.MoviesRepository
import com.claudiogalvaodev.filmes.ui.viewmodel.DiscoverViewModel
import com.claudiogalvaodev.filmes.webclient.service.MovieService
import com.claudiogalvaodev.filmes.ui.viewmodel.HomeViewModel
import com.claudiogalvaodev.filmes.ui.viewmodel.MovieDetailsViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/3/"

private const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2OTZhOGE1NTU4NDVlZDg4MjkyOTI4MWZlYmEzZGFiZiIsInN1YiI6IjVlZDNlN2UwYWFlYzcxMDAxZjZlNzg5NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.pJAinu1wp6hVbUlLGjCYa20CtphxVq9CttkKRvyfVAQ"

val retrofitModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $TOKEN")
                    .build()
                chain.proceed(newRequest)
            })
            .build()
    }
    single<MovieService> { get<Retrofit>().create(MovieService::class.java) }
}

val daoModule = module {
    single { MoviesDatabase.getInstance(androidContext()).movieDao }
}

val repositoryModule = module {
    single { MoviesRepository(get(), get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DiscoverViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
}

val appModules = listOf(
    retrofitModule, repositoryModule, viewModelModule, daoModule
)