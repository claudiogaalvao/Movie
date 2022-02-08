package com.claudiogalvaodev.moviemanager.usecases

data class GetMovieDetailsUseCases(
    val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    val getMovieProvidersUseCase: GetMovieProvidersUseCase,
    val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    val getMovieCollectionUseCase: GetMovieCollectionUseCase
)