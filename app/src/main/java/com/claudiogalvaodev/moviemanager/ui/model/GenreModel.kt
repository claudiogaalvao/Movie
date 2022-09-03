package com.claudiogalvaodev.moviemanager.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreModel(
    val id: Int,
    val name: String
): Parcelable