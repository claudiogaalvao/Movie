package com.claudiogalvaodev.moviemanager.ui.model.enum

import com.google.gson.annotations.SerializedName

enum class MovieStatusModel(val status: String) {
    RUMORED("Rumored"),
    PLANNED("Planned"),
    IN_PRODUCTION("In Production"),
    POST_PRODUCTION("Post Production"),
    RELEASED("Released"),
    CANCELED("Canceled")
}