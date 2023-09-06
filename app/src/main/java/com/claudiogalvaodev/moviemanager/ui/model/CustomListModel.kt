package com.claudiogalvaodev.moviemanager.ui.model

import com.claudiogalvaodev.moviemanager.utils.enums.PosterSizes
import com.claudiogalvaodev.moviemanager.utils.image.getFullUrl

data class CustomListModel(
    val id: Int,
    val name: String,
    val posterPath: String,
    val movies: List<MovieModel>
) {

    fun getPosterUrl(imageSize: PosterSizes = PosterSizes.W_500) =
        getFullUrl(posterPath, imageSize)

}