package com.claudiogalvaodev.moviemanager.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.claudiogalvaodev.moviemanager.Provider
import com.claudiogalvaodev.moviemanager.UserPreferences

class UserPreferencesRepository(
    private val context: Context,
    private val dataStore: DataStore<UserPreferences>
): IUserPreferencesRepository {
    override suspend fun getSelectedProviders(): LiveData<List<Provider>> {
        return dataStore.data
            .asLiveData()
            .switchMap { MutableLiveData(it.providersUserSelectedList) }
    }

    override suspend fun saveSelectedProviders(providers: List<Provider>) {
         dataStore.updateData { userPreferences ->
             userPreferences
                 .toBuilder()
                 .addAllProvidersUserSelected(providers)
                 .build()
         }
    }

    override suspend fun clearAllSelectedProviders() {
        dataStore.updateData {
            it.toBuilder().clear().build()
        }
    }

}
