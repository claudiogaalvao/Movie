package com.claudiogalvaodev.moviemanager.ui.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Salvar no sharedPreferences se já passou pelo onboarding
// Salvar os streamings no banco de dados

class OnboardingViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(StreamingsUiState())
    val uiState: StateFlow<StreamingsUiState> = _uiState.asStateFlow()


}