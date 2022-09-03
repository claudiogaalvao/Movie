package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.data.repository.IMoviesRepository
import com.claudiogalvaodev.moviemanager.ui.model.PersonModel

class SearchPeopleUseCase(
    private val repository: IMoviesRepository
) {

    // TODO Controlo a página aqui ou pelo view model?
    private var currentPage = 1

    suspend operator fun invoke(
        query: String,
        isUpdate: Boolean = false
    ): Result<List<PersonModel>> {
        if(isUpdate) currentPage = 1
        val peopleResult = repository.searchPeople(currentPage, query)

        if(peopleResult.isSuccess) {
            currentPage++
            val movies = peopleResult.getOrDefault(emptyList())
            return Result.success(movies)
        }
        return peopleResult
    }
}