package com.claudiogalvaodev.moviemanager.data.webclient.dto.movie

data class MovieProvidersResponseDto(
    val id: Long,
    val results: HashMap<String, ProvidersOptionsDto>
)