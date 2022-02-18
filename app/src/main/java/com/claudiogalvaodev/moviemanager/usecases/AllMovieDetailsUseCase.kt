package com.claudiogalvaodev.moviemanager.usecases

data class AllMovieDetailsUseCase(
    val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    val getMovieProvidersUseCase: GetMovieProvidersUseCase,
    val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    val getMovieCollectionUseCase: GetMovieCollectionUseCase,
    val getAllMyListsUseCase: GetAllMyListsUseCase,
    val createNewListOnMyListsUseCase: CreateNewListOnMyListsUseCase,
    val saveMovieToMyListUseCase: SaveMovieToMyListUseCase,
    val removeMovieFromMyListUseCase: RemoveMovieFromMyListUseCase,
    val checkIsMovieSavedUseCase: CheckIsMovieSavedUseCase,
    val getAllMoviesSavedUseCase: GetAllMoviesSavedUseCase,
)