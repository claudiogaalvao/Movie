package com.claudiogalvaodev.moviemanager.usecases

data class AllMovieDetailsUseCase(
    val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    val getMovieProvidersUseCase: GetMovieProvidersUseCase,
    val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    val getMovieCollectionUseCase: GetMovieCollectionUseCase,
    val getAllUserListsUseCase: GetAllUserListsUseCase,
    val createNewListOnMyListsUseCase: CreateNewListOnMyListsUseCase,
    val saveMovieOnMyListUseCase: SaveMovieOnUserListUseCase,
    val removeMovieFromMyListUseCase: RemoveMovieFromMyListUseCase,
    val checkIsMovieSavedUseCase: CheckIsMovieSavedUseCase,
    val getAllMoviesSavedUseCase: GetAllMoviesSavedUseCase,
    val getVideosFromMovieUseCase: GetVideosFromMovieUseCase
)