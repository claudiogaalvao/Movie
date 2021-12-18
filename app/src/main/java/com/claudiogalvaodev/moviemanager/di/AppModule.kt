package com.claudiogalvaodev.moviemanager.di

import com.claudiogalvaodev.moviemanager.BuildConfig
import com.claudiogalvaodev.moviemanager.data.bd.MoviesDatabase
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository
import com.claudiogalvaodev.moviemanager.ui.viewmodel.ExploreViewModel
import com.claudiogalvaodev.moviemanager.webclient.service.MovieService
import com.claudiogalvaodev.moviemanager.ui.viewmodel.HomeViewModel
import com.claudiogalvaodev.moviemanager.ui.viewmodel.MovieDetailsViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

private const val BASE_URL = BuildConfig.MOVIEDB_BASE_URL
private const val TOKEN = BuildConfig.MOVIEDB_TOKEN

val retrofitModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        val currentDeviceLanguage = Locale.getDefault().toLanguageTag()
        val currentDeviceRegion = Locale.getDefault().country

        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                var newRequest = chain.request()
                val url = newRequest.url.newBuilder()
                    .addQueryParameter("language", currentDeviceLanguage)
                    .addQueryParameter("region", currentDeviceRegion)
                    .build()

                newRequest = newRequest.newBuilder()
                    .addHeader("Authorization", "Bearer $TOKEN")
                    .url(url)
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
    viewModel { ExploreViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
}

val appModules = listOf(
    retrofitModule, repositoryModule, viewModelModule, daoModule
)