package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.model.Video
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository

class GetVideosFromMovieUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Int) = repository.getVideos(movieId)

}