package com.claudiogalvaodev.moviemanager.model

import android.os.Parcelable
import com.claudiogalvaodev.moviemanager.BuildConfig
import com.claudiogalvaodev.moviemanager.utils.enum.BackdropSizes
import com.claudiogalvaodev.moviemanager.utils.enum.PosterSizes
import kotlinx.parcelize.Parcelize

@Parcelize
class Employe(
    val adult: Boolean,
    val gender: Long,
    val id: Long,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String?,
    val cast_id: Long,
    val character: String,
    val credit_id: String,
    val order: Long
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