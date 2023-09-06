package com.claudiogalvaodev.moviemanager.ui.model

import com.claudiogalvaodev.moviemanager.utils.enums.PosterSizes
import com.claudiogalvaodev.moviemanager.utils.image.getFullUrl

data class ProductionCompanyModel(
    val id: Int,
    val name: String,
    val originCountry: String,
    val logoPath: String
) {

    fun getLogoImageUrl(imageSize: PosterSizes = PosterSizes.W_500) =
        getFullUrl(logoPath, imageSize)

}