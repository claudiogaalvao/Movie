package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.model.Collection
import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetMovieCollectionUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Int): Result<Collection?> {
        val collectionResult = repository.getCollection(movieId)
        if(collectionResult.isSuccess) {
            val collection = collectionResult.getOrNull()
            if(collection != null) {
                val sortedCollection = Collection(
                    id = collection.id,
                    name = collection.name,
                    poster_path = collection.poster_path,
                    backdrop_path = collection.backdrop_path,
                    parts = sortCollection(collection.parts)
                )
                return Result.success(sortedCollection)
            }
        }
        return collectionResult
    }

    private fun sortCollection(movies: List<Movie>): List<Movie> {
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return movies.filter { movie -> movie.release_date.isNotBlank() }.sortedBy { movie ->
            LocalDate.parse(movie.release_date, dateTimeFormatter)
        }
    }

}