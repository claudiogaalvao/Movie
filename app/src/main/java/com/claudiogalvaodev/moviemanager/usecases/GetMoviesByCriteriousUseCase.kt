package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.model.Employe
import com.claudiogalvaodev.moviemanager.data.model.Filter
import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository
import com.claudiogalvaodev.moviemanager.utils.OrderByConstants
import com.claudiogalvaodev.moviemanager.utils.enums.FilterType
import com.google.gson.Gson
import java.time.LocalDate

class GetMoviesByCriteriousUseCase(
    private val repository: MoviesRepository
) {

    private var currentPage = 1

    suspend operator fun invoke(
        criterious: List<Filter>,
        isUpdate: Boolean = false
    ): Result<List<Movie>?> {
        val currentDate = LocalDate.now()
        if(isUpdate) currentPage = 1
        val sortBy = (criterious.find { filter -> filter.type == FilterType.SORT_BY })?.currentValue ?: OrderByConstants.POPULARITY_DESC
        val withGenres = (criterious.find { filter -> filter.type == FilterType.GENRES })?.currentValue ?: ""
        val withPeople = convertPeopleFromJson(criterious)
        val year = (criterious.find { filter -> filter.type == FilterType.YEARS })?.currentValue ?: ""

//        val runtimeSelected = (criterious.find { filter -> filter.type == FilterType.RUNTIME })?.currentValue ?: ""
//        val withRuntimeLte: Int = defineRuntimeLte(runtimeSelected)
//        val withRuntimeGte: Int = defineRuntimeGte(runtimeSelected)

        val voteCount = if(sortBy == OrderByConstants.VOTE_AVERAGE_DESC) 1000 else 0

        val moviesResult = repository.getMoviesByCriterious(
            page = currentPage,
            currentDate = currentDate.toString(),
            sortBy = sortBy,
            withGenres = withGenres,
            voteCount = voteCount,
            withPeople = withPeople,
            year = year)
        if(moviesResult.isSuccess) {
            currentPage++
            val validMovies = removeInvalidMovies(moviesResult.getOrDefault(emptyList()))
            return Result.success(validMovies)
        }
        return moviesResult
    }

    private fun convertPeopleFromJson(criterious: List<Filter>): String {
        val withPeopleJson =
            (criterious.find { filter -> filter.type == FilterType.PEOPLE })?.currentValue ?: ""
        if(withPeopleJson.toIntOrNull() != null) return withPeopleJson
        val withPeople = if (withPeopleJson.isNotBlank()) {
            val withPeopleList =
                Gson().fromJson(withPeopleJson, Array<Employe>::class.java).asList()
            generatePeopleSelectedConcatened(withPeopleList)
        } else {
            withPeopleJson
        }
        return withPeople
    }

    private fun removeInvalidMovies(movies: List<Movie>): List<Movie> {
        val justMoviesWithPosterAndBackdropImage = movies.filter { movie ->
            movie.poster_path != null || movie.backdrop_path != null
        }
        return justMoviesWithPosterAndBackdropImage
    }

    fun generatePeopleSelectedConcatened(peopleSelected: List<Employe>): String {
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