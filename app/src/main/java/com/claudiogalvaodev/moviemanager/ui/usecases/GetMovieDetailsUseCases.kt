package com.claudiogalvaodev.moviemanager.ui.usecases

data class GetMovieDetailsUseCases(
    val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    val getMovieProvidersUseCase: GetMovieProvidersUseCase,
    val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    val getMovieCollectionUseCase: GetMovieCollectionUseCase
)