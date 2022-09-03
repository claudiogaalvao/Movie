package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.data.repository.IMoviesRepository
import com.claudiogalvaodev.moviemanager.ui.model.PersonModel

class GetAllPeopleUseCase(
    private val repository: IMoviesRepository
) {

    private var currentPage = 1

    suspend operator fun invoke(isInitialize: Boolean): Result<List<PersonModel>> {
        if(isInitialize) currentPage = 1
        val popularPeopleResult = repository.getAllPopularPeople(page = currentPage)
        if(popularPeopleResult.isSuccess) {
            currentPage++
            return Result.success(popularPeopleResult.getOrDefault(emptyList()))
        }
        return popularPeopleResult
    }
}