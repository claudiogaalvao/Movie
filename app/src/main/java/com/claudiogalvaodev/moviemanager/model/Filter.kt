package com.claudiogalvaodev.moviemanager.model

import com.claudiogalvaodev.moviemanager.utils.enum.FilterType

data class Filter(
    val type: FilterType,
    val name: String,
    val currentValue: Any
)