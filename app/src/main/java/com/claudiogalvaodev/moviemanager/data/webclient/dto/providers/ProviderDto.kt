package com.claudiogalvaodev.moviemanager.data.webclient.dto.providers

import com.google.gson.annotations.SerializedName

data class ProviderDto(
    @SerializedName("provider_id")
    val id: Int,
    @SerializedName("provider_name")
    val name: String,
    @SerializedName("display_priorities")
    val displayPriorities: DisplayPriorities,
    @SerializedName("display_priority")
    val displayPriority: Int,
    @SerializedName("logo_path")
    val logoPath: String
)