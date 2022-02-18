package com.claudiogalvaodev.moviemanager.ui.peopleandcompanies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.data.model.Employe
import com.claudiogalvaodev.moviemanager.usecases.GetMovieCreditsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PeopleAndCompaniesViewModel(
    val movieId: Int,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _stars = MutableStateFlow<List<Employe>?>(emptyList())
    val stars = _stars.asStateFlow()

    fun getMovieCredits() = viewModelScope.launch(dispatcher) {
        getMovieCreditsUseCase.invoke(movieId)
        _stars.emit(getMovieCreditsUseCase.stars.value)
    }

}