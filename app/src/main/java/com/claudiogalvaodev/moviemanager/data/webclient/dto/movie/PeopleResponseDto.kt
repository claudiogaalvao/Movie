package com.claudiogalvaodev.moviemanager.data.webclient.dto.movie

import com.claudiogalvaodev.moviemanager.data.webclient.dto.movie.PersonDto

data class PeopleResponseDto(
    val page: Int,
    val results: List<PersonDto>
)