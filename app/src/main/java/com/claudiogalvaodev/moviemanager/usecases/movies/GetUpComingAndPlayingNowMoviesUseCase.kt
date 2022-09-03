package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.data.repository.IMoviesRepository
import com.claudiogalvaodev.moviemanager.ui.model.HighlightsModel

class GetUpComingAndPlayingNowMoviesUseCase(
    private val repository: IMoviesRepository,
) {

    suspend operator fun invoke(): Result<HighlightsModel> {
        return repository.getHighlights()
    }

}