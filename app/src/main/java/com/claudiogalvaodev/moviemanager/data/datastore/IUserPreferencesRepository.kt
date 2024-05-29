package com.claudiogalvaodev.moviemanager.data.datastore

import androidx.lifecycle.LiveData
import com.claudiogalvaodev.moviemanager.Provider

interface IUserPreferencesRepository {

    suspend fun getSelectedProviders(): LiveData<List<Provider>>

    suspend fun saveSelectedProviders(providers: List<Provider>)

    suspend fun clearAllSelectedProviders()

}
