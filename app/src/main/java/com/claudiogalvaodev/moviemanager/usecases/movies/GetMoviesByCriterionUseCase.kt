package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.data.repository.IMoviesRepository
import com.claudiogalvaodev.moviemanager.data.webclient.dto.movie.PersonDto
import com.claudiogalvaodev.moviemanager.ui.model.FilterModel
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.claudiogalvaodev.moviemanager.utils.OrderByConstants
import com.claudiogalvaodev.moviemanager.utils.enums.FilterType
import com.google.gson.Gson
import java.time.LocalDate

class GetMoviesByCriterionUseCase(
    private val repository: IMoviesRepository
) {

    private var currentPage = 1

    suspend operator fun invoke(
        criterion: List<FilterModel>,
        isUpdate: Boolean = false
    ): Result<List<MovieModel>?> {
        val currentDate = LocalDate.now()
        if(isUpdate) currentPage = 1
        val sortBy = (criterion.find { filter -> filter.type == FilterType.SORT_BY })?.currentValue ?: OrderByConstants.POPULARITY_DESC
        val withGenres = (criterion.find { filter -> filter.type == FilterType.GENRES })?.currentValue ?: ""
        val withPeople = convertPeopleFromJson(criterion)
        val year = (criterion.find { filter -> filter.type == FilterType.YEARS })?.currentValue ?: ""

        val voteCount = if(sortBy == OrderByConstants.VOTE_AVERAGE_DESC) 1000 else 0

        val moviesResult = repository.getMoviesByCriterion(
            page = currentPage,
            currentDate = currentDate.toString(),
            sortBy = sortBy,
            withGenres = withGenres,
            voteCount = voteCount,
            withPeople = withPeople,
            year = year)

        if(moviesResult.isSuccess) {
            currentPage++
            val validMovies = removeInconsistentData(withPeople, moviesResult.getOrDefault(emptyList()))
            return Result.success(validMovies)
        }
        return moviesResult
    }

    private fun convertPeopleFromJson(criterious: List<FilterModel>): String {
        val withPeopleJson =
            (criterious.find { filter -> filter.type == FilterType.PEOPLE })?.currentValue ?: ""
        if(withPeopleJson.toIntOrNull() != null) return withPeopleJson
        val withPeople = if (withPeopleJson.isNotBlank()) {
            val withPeopleList =
                Gson().fromJson(withPeopleJson, Array<PersonDto>::class.java).asList()
            generatePeopleSelectedConcatenated(withPeopleList)
        } else {
            withPeopleJson
        }
        return withPeople
    }

    private fun removeInconsistentData(withPeopleId: String, movieModels: List<MovieModel>): List<MovieModel> {
        val mutableList = mutableListOf<MovieModel>()
        val thId = "1136406"
        val lgId = "237405"

        mutableList.addAll(if (withPeopleId.contains(thId)) {
            movieModels.filter { movie -> movie.id != 580489 }
        } else if (withPeopleId.contains(lgId)) {
            movieModels.filter { movie -> movie.id != 68718 }
        } else {
            movieModels
        })
        return mutableList
    }

    private fun generatePeopleSelectedConcatenated(peopleSelected: List<PersonDto>): String {
        var peopleSelectedConcat = ""
        for(person in peopleSelected) {
            if(peopleSelected.first() == person) {
                peopleSelectedConcat += "${person.id}"
                continue
            }
            peopleSelectedConcat += ",${person.id}"
        }
        return peopleSelectedConcat
    }

}