package com.claudiogalvaodev.moviemanager.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import com.claudiogalvaodev.moviemanager.BuildConfig
import com.claudiogalvaodev.moviemanager.data.bd.CineSeteDatabase
import com.claudiogalvaodev.moviemanager.data.bd.datasource.CustomListsLocalDatasource
import com.claudiogalvaodev.moviemanager.data.bd.datasource.ICustomListsLocalDatasource
import com.claudiogalvaodev.moviemanager.data.bd.datasource.IScheduledNotificationsLocalDatasource
import com.claudiogalvaodev.moviemanager.data.bd.datasource.ScheduledNotificationsLocalDatasource
import com.claudiogalvaodev.moviemanager.data.repository.*
import com.claudiogalvaodev.moviemanager.data.webclient.datasource.movie.IMovieRemoteDatasource
import com.claudiogalvaodev.moviemanager.data.webclient.datasource.movie.MovieRemoteDatasource
import com.claudiogalvaodev.moviemanager.data.webclient.service.MovieClient
import com.claudiogalvaodev.moviemanager.ui.explore.ExploreMoviesViewModel
import com.claudiogalvaodev.moviemanager.ui.filter.FiltersViewModel
import com.claudiogalvaodev.moviemanager.ui.home.HomeViewModel
import com.claudiogalvaodev.moviemanager.ui.customLists.CustomListsViewModel
import com.claudiogalvaodev.moviemanager.ui.customLists.details.CustomListsDetailsViewModel
import com.claudiogalvaodev.moviemanager.ui.menu.schedulednotifications.ScheduledNotificationsViewModel
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsViewModel
import com.claudiogalvaodev.moviemanager.ui.peopleandcompanies.PeopleAndCompaniesViewModel
import com.claudiogalvaodev.moviemanager.ui.peopledetails.PeopleDetailsViewModel
import com.claudiogalvaodev.moviemanager.ui.search.SearchViewModel
import com.claudiogalvaodev.moviemanager.ui.speciallist.SpecialListViewModel
import com.claudiogalvaodev.moviemanager.usecases.customlists.*
import com.claudiogalvaodev.moviemanager.usecases.movies.*
import com.claudiogalvaodev.moviemanager.usecases.notification.GetAllScheduledNotification
import com.claudiogalvaodev.moviemanager.usecases.notification.HasMovieReleaseScheduledNotification
import com.claudiogalvaodev.moviemanager.usecases.notification.ScheduleMovieReleaseNotificationUseCase
import com.claudiogalvaodev.moviemanager.utils.notification.CineSeteNotificationManager
import com.claudiogalvaodev.moviemanager.utils.notification.ICineSeteNotificationManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

private const val MOVIEDB_BASE_URL = BuildConfig.MOVIEDB_BASE_URL
private const val TOKEN = BuildConfig.MOVIEDB_TOKEN

val retrofitModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(MOVIEDB_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        val currentDeviceLanguage = Locale.getDefault().toLanguageTag()
        val currentDeviceRegion = "BR"

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
                .addQueryParameter("watch_region", currentDeviceRegion)
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
    single<MovieClient> { get<Retrofit>().create(MovieClient::class.java) }
}

val daoModule = module {
    factory { CoroutineScope(Dispatchers.IO) }

    single { CineSeteDatabase.getInstance(androidContext()).customListsDao }
    single { CineSeteDatabase.getInstance(androidContext()).moviesSavedDao }
    single { CineSeteDatabase.getInstance(androidContext()).scheduledNotificationsDao }
}

val dataModule = module {
    single<IMovieRemoteDatasource> { MovieRemoteDatasource(get()) }
    single<IMoviesRepository> { MoviesRepository(get()) }

    single<ICustomListsLocalDatasource> { CustomListsLocalDatasource(get(), get()) }
    single<ICustomListsRepository> { CustomListsRepository(get()) }

    single<IScheduledNotificationsLocalDatasource> { ScheduledNotificationsLocalDatasource(get()) }
    single<IScheduledNotificationsRepository> { ScheduledNotificationsRepository(get()) }
}

val viewModelModule = module {
    single { GetTrendingWeekMoviesUseCase(get()) }
    single { GetUpComingAndPlayingNowMoviesUseCase(get()) }
    single { GetMovieDetailsUseCase(get()) }
    single { GetMovieProvidersUseCase(get()) }
    single { GetMovieCreditsUseCase(get()) }
    single { GetMovieCollectionUseCase(get()) }
    single { GetMoviesByCriterionUseCase(get()) }
    single { GetAllGenresUseCase(get()) }
    single { GetAllPeopleUseCase(get()) }
    single { GetPersonDetailsUseCase(get()) }
    single { SearchMoviesUseCase(get()) }
    single { CreateNewCustomListUseCase(get()) }
    single { GetAllCustomListsUseCase(get()) }
    single { SaveMovieOnCustomListUseCase(get()) }
    single { GetMoviesByListIdUseCase(get()) }
    single { DeleteCustomListUseCase(get()) }
    single { RemoveMovieFromCustomListUseCase(get()) }
    single { SearchPeopleUseCase(get()) }
    single { GetVideosFromMovieUseCase(get()) }
    single { ScheduleMovieReleaseNotificationUseCase(get(), get()) }
    single { HasMovieReleaseScheduledNotification(get()) }
    single { GetAllScheduledNotification(get()) }

    single {
        AllMovieDetailsUseCase(
            getMovieDetailsUseCase = get(),
            getMovieProvidersUseCase = get(),
            getMovieCreditsUseCase = get(),
            getMovieCollectionUseCase = get(),
            getAllCustomListsUseCase = get(),
            createNewCustomListUseCase = get(),
            saveMovieOnCustomListUseCase = get(),
            removeMovieFromCustomListUseCase = get(),
            getVideosFromMovieUseCase = get()
        )
    }

    single {
        Firebase.firestore
    }

    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { ExploreMoviesViewModel(get()) }
    viewModel { FiltersViewModel(get(), get(), get()) }
    viewModel { (movieId: Int, releaseDate: String) ->
        MovieDetailsViewModel(
            movieId = movieId,
            releaseDate = releaseDate,
            allMovieDetailsUseCase = get()
        )
    }
    viewModel { (personId: Int, leastOneMovieId: Int) ->
        PeopleDetailsViewModel(
            personId = personId,
            leastOneMovieId = leastOneMovieId,
            getMovieDetailsUseCase = get(),
            getMoviesByCriterionUseCase = get(),
            getPersonDetailsUseCase = get()
        )
    }
    viewModel { SearchViewModel(get()) }
    viewModel { CustomListsViewModel(
        createNewCustomListUseCase = get(),
        getAllCustomListsUseCase = get()
    ) }
    viewModel { (customListId: Int) ->
        CustomListsDetailsViewModel(
            customListId = customListId,
            getMoviesByListIdUseCase = get(),
            deleteCustomListUseCase = get()
        )
    }
    viewModel { (movieId: Int) ->
        PeopleAndCompaniesViewModel(
            movieId = movieId,
            getMovieCreditsUseCase = get()
        )
    }
    viewModel { (eventId: String) ->
        SpecialListViewModel(
            eventId = eventId,
            firestoreDB = get()
        )
    }
    viewModel { ScheduledNotificationsViewModel(
        getAllScheduledNotification = get()
    ) }
}

val notificationsModule = module {
    single<ICineSeteNotificationManager> {
        CineSeteNotificationManager(androidContext())
    }
}

val appModules = listOf(
    notificationsModule, retrofitModule, dataModule, viewModelModule, daoModule
)