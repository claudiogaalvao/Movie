package com.claudiogalvaodev.moviemanager.data.model

import android.os.Parcelable
import com.claudiogalvaodev.moviemanager.BuildConfig
import com.claudiogalvaodev.moviemanager.utils.enums.PosterSizes
import kotlinx.parcelize.Parcelize

@Parcelize
class Employe(
    val id: Long,
    val adult: Boolean,
    val gender: Long,
    val known_for_department: String,
    val known_for: List<Movie>? = null,
    val name: String,
    val original_name: String?,
    val popularity: Double,
    val profile_path: String?,
    val cast_id: Long? = null,
    val character: String?,
    val credit_id: String?,
    val order: Long? = null,
    var position: Int? = null,
    val birthday: String? = null,
    val deathday: String? = null,
    val place_of_birth: String? = null,
    val biography: String? = null
) : Parcelable {
    fun getProfileImageUrl(imageSize: PosterSizes = PosterSizes.W_500) : String {
        return "${BuildConfig.MOVIEDB_IMAGE_BASE_URL}${getPosterSize(imageSize)}$profile_path"
    }

    private fun getPosterSize(size: PosterSizes): String {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employe

        if (id != other.id) return false
        if (original_name != other.original_name) return false
        if (profile_path != other.profile_path) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + original_name.hashCode()
        result = 31 * result + (profile_path?.hashCode() ?: 0)
        return result
    }


}