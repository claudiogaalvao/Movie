package com.claudiogalvaodev.moviemanager.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.claudiogalvaodev.moviemanager.repository.MoviesRepository

class MovieDetailsViewModel(
    private val repository: MoviesRepository
): ViewModel() {

}