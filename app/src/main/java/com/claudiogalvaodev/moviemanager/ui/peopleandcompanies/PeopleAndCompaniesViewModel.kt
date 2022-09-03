package com.claudiogalvaodev.moviemanager.ui.peopleandcompanies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claudiogalvaodev.moviemanager.ui.model.PersonModel
import com.claudiogalvaodev.moviemanager.usecases.movies.GetMovieCreditsUseCase
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

    private val _actors = MutableStateFlow<List<PersonModel>?>(emptyList())
    val actors = _actors.asStateFlow()

    fun getMovieCredits() = viewModelScope.launch(dispatcher) {
        val creditsModelResult = getMovieCreditsUseCase.invoke(movieId)
        if (creditsModelResult.isSuccess) {
            val creditsModel = creditsModelResult.getOrNull()
            creditsModel?.let {
                _actors.emit(it.actors)
            }
        }
    }

}