package com.claudiogalvaodev.moviemanager.data.webclient.dto.movie

data class ProvidersOptionsDto(
    val link: String,
    val flatrate: List<ProviderDto>?,
    val rent: List<ProviderDto>?,
    val buy: List<ProviderDto>?
)