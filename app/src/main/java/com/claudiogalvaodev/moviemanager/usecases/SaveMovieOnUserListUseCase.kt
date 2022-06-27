package com.claudiogalvaodev.moviemanager.usecases

import com.claudiogalvaodev.moviemanager.data.model.Movie
import com.claudiogalvaodev.moviemanager.data.repository.MoviesRepository
import com.claudiogalvaodev.moviemanager.ui.model.BottomSheetOfListsUI

class SaveMovieOnUserListUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(userListSelected: BottomSheetOfListsUI, movie: Movie): Result<Unit> {
        val userList = repository.getUserListById(userListId = userListSelected.id.toInt())
        repository.saveMovieOnUserList(userListEntity = userList, movie = movie)
        return Result.success(Unit)
    }

}