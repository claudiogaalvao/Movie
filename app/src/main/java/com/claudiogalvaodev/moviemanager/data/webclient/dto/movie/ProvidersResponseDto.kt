package com.claudiogalvaodev.moviemanager.data.webclient.dto.movie

data class ProvidersResponseDto(
    val id: Long,
    val results: HashMap<String, ProvidersOptionsDto>
)