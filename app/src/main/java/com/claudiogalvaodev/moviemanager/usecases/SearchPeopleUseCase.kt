package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.model.Employe
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class SearchPeopleUseCase(
    private val repository: MoviesRepository
) {

    private var currentPage = 1

    suspend operator fun invoke(
        query: String,
        isUpdate: Boolean = false
    ): Result<List<Employe>> {
        if(isUpdate) currentPage = 1
        val peopleResult = repository.searchPeople(currentPage, query)

        if(peopleResult.isSuccess) {
            currentPage++
            val validPeople = removeInvalidPeople(peopleResult.getOrDefault(emptyList()))
            return Result.success(validPeople)
        }
        return peopleResult
    }

    private fun removeInvalidPeople(people: List<Employe>): List<Employe> {
        val justPeopleWithProfileImage = people.filter { person ->
            person.profile_path != null
        }
        return justPeopleWithProfileImage
    }
}