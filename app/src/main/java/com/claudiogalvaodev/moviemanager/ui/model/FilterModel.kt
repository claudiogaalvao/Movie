package com.claudiogalvaodev.moviemanager.ui.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.claudiogalvaodev.moviemanager.utils.enums.FilterType
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterModel(
    val type: FilterType,
    @StringRes val nameRes: Int?,
    var currentValue: String
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FilterModel

        if (type != other.type) return false
        if (nameRes != other.nameRes) return false
        if (currentValue != other.currentValue) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + nameRes.hashCode()
        result = 31 * result + currentValue.hashCode()
        return result
    }
}

fun List<FilterModel>.getCurrentValue(type: FilterType) = this
    .find { filter -> filter.type == type }?.currentValue