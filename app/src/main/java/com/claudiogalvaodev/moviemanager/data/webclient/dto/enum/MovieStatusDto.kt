package com.claudiogalvaodev.moviemanager.data.webclient.dto.enum

import com.google.gson.annotations.SerializedName

enum class MovieStatusDto(val status: String) {
    @SerializedName("Rumored")
    RUMORED("Rumored"),

    @SerializedName("Planned")
    PLANNED("Planned"),

    @SerializedName("In Production")
    IN_PRODUCTION("In Production"),

    @SerializedName("Post Production")
    POST_PRODUCTION("Post Production"),

    @SerializedName("Released")
    RELEASED("Released"),

    @SerializedName("Canceled")
    CANCELED("Canceled")
}