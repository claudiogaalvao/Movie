package com.claudiogalvaodev.moviemanager.data.webclient.dto.movie

import com.claudiogalvaodev.moviemanager.ui.model.ProviderModel
import com.google.gson.annotations.SerializedName

data class ProviderDto(
    @SerializedName("provider_id")
    val id: Int,
    @SerializedName("provider_name")
    val name: String,
    @SerializedName("display_priority")
    val displayPriority: Int,
    @SerializedName("logo_path")
    val logoPath: String
)

fun ProviderDto.toModel(): ProviderModel = ProviderModel(
    id = this.id,
    name = this.name,
    logoPath = this.logoPath
)

fun List<ProviderDto>?.toListOfProviderModel(): List<ProviderModel> = this?.let { providers ->
    providers.map { providerDto -> providerDto.toModel() }
} ?: emptyList()