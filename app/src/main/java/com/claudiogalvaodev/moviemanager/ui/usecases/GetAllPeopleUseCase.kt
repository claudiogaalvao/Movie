package com.claudiogalvaodev.moviemanager.ui.usecases

import com.claudiogalvaodev.moviemanager.model.Employe
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository

class GetAllPeopleUseCase(
    private val repository: MoviesRepository
) {

    private var currentPage = 1

    suspend operator fun invoke(): Result<List<Employe>> {
        val popularPeopleResult = repository.getAllPopularPeople(page = currentPage)
        if(popularPeopleResult.isSuccess) {
            currentPage++
            val validPeopleProfiles = removeInvalidPersonProfile(popularPeopleResult.getOrDefault(emptyList()))
            return Result.success(validPeopleProfiles)
        }
        return popularPeopleResult
    }

    private fun removeInvalidPersonProfile(people: List<Employe>): List<Employe> {
        val justPeopleWithPosterAndBackdropImage = people.filter { person ->
            person.profile_path != null
        }
        return justPeopleWithPosterAndBackdropImage
    }
}