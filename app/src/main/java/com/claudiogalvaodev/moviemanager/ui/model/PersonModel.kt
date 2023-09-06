package com.claudiogalvaodev.moviemanager.ui.model

import com.claudiogalvaodev.moviemanager.utils.enums.PosterSizes
import com.claudiogalvaodev.moviemanager.utils.image.getFullUrl

data class PersonModel(
    val id: Int,
    val adult: Boolean,
    val gender: Int,
    val knownForDepartment: String,
    val knownFor: List<MovieModel>,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?,
    val castId: Int,
    val character: String,
    val creditId: String,
    val birthday: String?,
    val deathday: String?,
    val placeOfBirth: String?,
    val biography: String,
    val homepage: String?,
    val job: String?,
    val department: String?,
    val order: Int?,
    var position: Int = 0,
) {

    fun getProfileImageUrl(imageSize: PosterSizes = PosterSizes.W_500) =
        getFullUrl(profilePath.toString(), imageSize)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersonModel

        if (id != other.id) return false
        if (originalName != other.originalName) return false
        if (profilePath != other.profilePath) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + originalName.hashCode()
        result = 31 * result + (profilePath?.hashCode() ?: 0)
        return result
    }

}