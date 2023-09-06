package com.claudiogalvaodev.moviemanager.data.webclient.dto.providers

import com.claudiogalvaodev.moviemanager.ui.model.ProviderModel
import com.google.gson.annotations.SerializedName

data class ProvidersResponseDto(
    @SerializedName("results")
    val providers: List<ProviderDto>
)

fun ProvidersResponseDto.toModel() = this.providers.map { provider ->
    ProviderModel(
        id = provider.id,
        name = provider.name,
        displayPriority = provider.displayPriorities.BR,
        logoPath = provider.logoPath
    )
}