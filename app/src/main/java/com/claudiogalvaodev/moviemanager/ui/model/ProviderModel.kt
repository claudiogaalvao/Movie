package com.claudiogalvaodev.moviemanager.ui.model

import com.claudiogalvaodev.moviemanager.utils.enums.LogoSizes
import com.claudiogalvaodev.moviemanager.utils.image.getFullUrl

data class ProviderModel(
    val id: Int,
    val name: String,
    val logoPath: String,
    val isSelected: Boolean = false
) {

    fun getLogoImageUrl(imageSize: LogoSizes = LogoSizes.ORIGINAL) =
        getFullUrl(logoPath, imageSize)

}
