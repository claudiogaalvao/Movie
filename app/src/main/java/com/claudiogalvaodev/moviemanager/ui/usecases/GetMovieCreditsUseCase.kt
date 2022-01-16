package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.model.Credits
import com.claudiogalvaodev.moviemanager.model.Employe
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GetMovieCreditsUseCase(
    private val repository: MoviesRepository
) {

    private val _directors = MutableStateFlow<List<Employe>?>(emptyList())
    val directors = _directors.asStateFlow()

    private val _stars = MutableStateFlow<List<Employe>?>(emptyList())
    val stars = _stars.asStateFlow()

    suspend operator fun invoke(movieId: Int) {
        val creditsResult = repository.getCredits(movieId)
        if(creditsResult.isSuccess) {
            val movieCredits = creditsResult.getOrDefault(null)
            if(movieCredits != null) {
                _stars.emit(filterStars(movieCredits))
                _directors.emit(filterDirectors(movieCredits))
            }
        }
    }

    private fun filterStars(credits: Credits): List<Employe> {
        return credits.cast.filter { employe -> employe.known_for_department == "Acting" && employe.profile_path != null }
    }

    private fun filterDirectors(credits: Credits): List<Employe> {
        val castDirectors = credits.cast.filter { employe -> employe.known_for_department == "Directing" && employe.profile_path != null }
        val crewDirectors = credits.crew.filter { employe -> employe.known_for_department == "Directing" && employe.profile_path != null }
        val directors: MutableList<Employe> = arrayListOf()
        directors.addAll(castDirectors)
        directors.addAll(crewDirectors)
        return directors.distinct().toList()
    }

}