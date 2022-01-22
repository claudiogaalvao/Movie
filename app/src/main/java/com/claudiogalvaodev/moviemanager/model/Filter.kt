package com.claudiogalvaodev.moviemanager.model

import android.os.Parcelable
import com.claudiogalvaodev.moviemanager.utils.enum.FilterType
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    val type: FilterType,
    val name: String,
    var currentValue: String
) : Parcelable