package com.claudiogalvaodev.moviemanager.utils

import com.claudiogalvaodev.moviemanager.utils.enums.BackdropSizes
import com.claudiogalvaodev.moviemanager.utils.enums.PosterSizes

fun getPosterSize(size: PosterSizes): String {
    return when(size) {
        PosterSizes.W_780 -> "w780"
        PosterSizes.W_500 -> "w500"
        PosterSizes.W_342 -> "w342"
        PosterSizes.W_185 -> "w185"
        PosterSizes.W_154 -> "w154"
        PosterSizes.W_92 -> "w92"
        else -> "original"
    }
}

fun getBackdropSize(size: BackdropSizes): String {
    return when(size) {
        BackdropSizes.W_1280 -> "w1280"
        BackdropSizes.W_780 -> "w780"
        BackdropSizes.W_300 -> "w300"
        else -> "original"
    }
}