package com.claudiogalvaodev.moviemanager.utils.image

import com.claudiogalvaodev.moviemanager.BuildConfig
import com.claudiogalvaodev.moviemanager.utils.enums.BackdropSizes
import com.claudiogalvaodev.moviemanager.utils.enums.LogoSizes
import com.claudiogalvaodev.moviemanager.utils.enums.PosterSizes

fun getFullUrl(path: String, size: BackdropSizes = BackdropSizes.ORIGINAL): String {
    return "${BuildConfig.MOVIEDB_IMAGE_BASE_URL}${size.value}$path"
}

fun getFullUrl(path: String, size: PosterSizes = PosterSizes.ORIGINAL): String {
    return "${BuildConfig.MOVIEDB_IMAGE_BASE_URL}${size.value}$path"
}

fun getFullUrl(path: String, size: LogoSizes = LogoSizes.ORIGINAL): String {
    return "${BuildConfig.MOVIEDB_IMAGE_BASE_URL}${size.value}$path"
}