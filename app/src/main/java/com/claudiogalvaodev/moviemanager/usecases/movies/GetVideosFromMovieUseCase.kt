package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.data.repository.IMoviesRepository

class GetVideosFromMovieUseCase(
    private val repository: IMoviesRepository
) {

    suspend operator fun invoke(movieId: Int) = repository.getVideos(movieId)

}