package com.claudiogalvaodev.moviemanager.ui.filter.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.data.repository.IProvidersRepository
import com.claudiogalvaodev.moviemanager.ui.model.ProviderModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FilterProviderViewModel(
    private val repository: IProvidersRepository
): ViewModel() {

    private val _providers = MutableStateFlow<List<ProviderModel>>(mutableListOf())
    val providers = _providers.asStateFlow()

    fun getProviders(initialSelection: List<Int>) = viewModelScope.launch {
        val popularProviders = repository.getPopularProviders()
        if(popularProviders.isSuccess) {
            popularProviders.getOrNull()?.let { providers ->
                _providers.emit(providers.map {
                    it.copy(isSelected = initialSelection.contains(it.id))
                })
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

}