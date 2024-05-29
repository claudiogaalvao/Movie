package com.claudiogalvaodev.moviemanager.ui.filter.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.ui.model.ProviderModel
import com.claudiogalvaodev.moviemanager.ui.model.toProvider
import com.claudiogalvaodev.moviemanager.usecases.movies.GetPopularProvidersAndUserSelectionUseCase
import com.claudiogalvaodev.moviemanager.usecases.movies.SaveSelectedProvidersUserCase
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FilterProviderViewModel(
    private val getPopularProvidersAndUserSelectionUseCase: GetPopularProvidersAndUserSelectionUseCase,
    private val saveSelectedProvidersUserCase: SaveSelectedProvidersUserCase
): ViewModel() {

    private val _providers = MutableStateFlow<List<ProviderModel>>(mutableListOf())
    val providers = _providers.asStateFlow()

    fun getProviders(initialSelection: List<Int>) = viewModelScope.launch {
        val providersResult = getPopularProvidersAndUserSelectionUseCase()
        if(providersResult.isSuccess) {
            providersResult.getOrNull()?.let { providers ->
                val allProviders = providers
                    .map { it.copy(isSelected = initialSelection.contains(it.id)) }
                _providers.emit(allProviders)
            }
        }
    }

    fun selectProvider(providerId: Int) = viewModelScope.launch {
        val providersList = _providers.value
        val selectedProvider = providersList.find { it.id == providerId }
        val updatedProvider = selectedProvider?.copy(isSelected = !selectedProvider.isSelected)
        _providers.value = providersList.map { if(it.id == providerId) updatedProvider!! else it }
    }

    fun getSelectedProvidersAsJson(): String {
        val selectedProvidersId = _providers.value
            .filter { it.isSelected }
            .map { it.id }
        return Gson().toJson(selectedProvidersId)
    }

    fun saveSelectedProviders() = viewModelScope.launch {
        // TODO: Review this implementation
        val selectedProviders = _providers.value
            .filter { it.isSelected }
            .map { it.toProvider() }
        saveSelectedProvidersUserCase(selectedProviders)
    }

}