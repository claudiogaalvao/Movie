package com.claudiogalvaodev.moviemanager.usecases.movies

import com.claudiogalvaodev.moviemanager.usecases.customlists.*

data class AllMovieDetailsUseCase(
    val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    val getMovieProvidersUseCase: GetMovieProvidersUseCase,
    val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    val getMovieCollectionUseCase: GetMovieCollectionUseCase,
    val getAllCustomListsUseCase: GetAllCustomListsUseCase,
    val createNewCustomListUseCase: CreateNewCustomListUseCase,
    val saveMovieOnCustomListUseCase: SaveMovieOnCustomListUseCase,
    val removeMovieFromCustomListUseCase: RemoveMovieFromCustomListUseCase,
    val getVideosFromMovieUseCase: GetVideosFromMovieUseCase
)