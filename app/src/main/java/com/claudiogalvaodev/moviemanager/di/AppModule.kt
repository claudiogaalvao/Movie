package com.claudiogalvaodev.moviemanager.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import com.claudiogalvaodev.moviemanager.BuildConfig
import com.claudiogalvaodev.moviemanager.data.bd.CineSeteDatabase
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository
import com.claudiogalvaodev.moviemanager.ui.explore.ExploreMoviesViewModel
import com.claudiogalvaodev.moviemanager.ui.filter.FiltersViewModel
import com.claudiogalvaodev.moviemanager.ui.home.HomeViewModel
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsViewModel
import com.claudiogalvaodev.moviemanager.ui.peopledetails.PeopleDetailsViewModel
import com.claudiogalvaodev.moviemanager.ui.usecases.*
import com.claudiogalvaodev.moviemanager.data.webclient.service.MovieService
import com.claudiogalvaodev.moviemanager.ui.mylists.MyListsViewModel
import com.claudiogalvaodev.moviemanager.ui.search.SearchViewModel
import okhttp3.*
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

        val onlineInterceptor = Interceptor { chain ->
            val response: Response = chain.proceed(chain.request())
            val maxAge = 60 * 60 * 24 // read from cache for 24 hours even if there is internet connection
            response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma")
                .build()
        }

        val offlineInterceptor = Interceptor { chain ->
            fun isInternetAvailable(context: Context): Boolean {
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                return capabilities?.hasCapability(NET_CAPABILITY_INTERNET) == true
            }

            var request: Request = chain.request()
            if (!isInternetAvailable(get())) {
                val maxStale = 60 * 60 * 24 * 2 // Offline cache available for 48 hours
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
            }
            chain.proceed(request)
        }

        val modifierRequestInterceptor = Interceptor { chain ->
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
        }

        val cacheSize = (100 * 1024 * 1024).toLong() // 100 MB
        val cache = Cache(androidContext().cacheDir, cacheSize)

        OkHttpClient.Builder()
            .addNetworkInterceptor(onlineInterceptor)
            .addInterceptor(offlineInterceptor)
            .addInterceptor(modifierRequestInterceptor)
            .cache(cache)
            .build()
    }
    single<MovieService> { get<Retrofit>().create(MovieService::class.java) }
}

val daoModule = module {
    single { CineSeteDatabase.getInstance(androidContext()).myListsDao }
}

val repositoryModule = module {
    single { MoviesRepository(get(), get()) }
}

val viewModelModule = module {
    single { GetMovieDetailsUseCases(get(), get(), get(), get()) }

    single { GetTrendingWeekMoviesUseCase(get()) }
    single { GetUpComingAndPlayingNowMoviesUseCase(get()) }
    single { GetMovieDetailsUseCase(get()) }
    single { GetMovieProvidersUseCase(get()) }
    single { GetMovieCreditsUseCase(get()) }
    single { GetMovieCollectionUseCase(get()) }
    single { GetMoviesByCriteriousUseCase(get()) }
    single { GetAllGenresUseCase(get()) }
    single { GetAllPeopleUseCase(get()) }
    single { GetPersonDetailsUseCase(get()) }
    single { SearchMoviesUseCase(get()) }
    single { CreateNewListOnMyListsUseCase(get()) }
    single { GetAllMyListsUseCase(get()) }

    viewModel { HomeViewModel(get(), get()) }
    viewModel { ExploreMoviesViewModel(get(), get()) }
    viewModel { FiltersViewModel(get(), get()) }
    viewModel { MovieDetailsViewModel(get(), get(), get()) }
    viewModel { PeopleDetailsViewModel(get(), get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { MyListsViewModel(get(), get()) }
}

val appModules = listOf(
    retrofitModule, repositoryModule, viewModelModule, daoModule
)