package com.claudiogalvaodev.moviemanager.data.webclient.dto.movie

import com.claudiogalvaodev.moviemanager.ui.model.CreditsModel

data class CreditsDto(
    val cast: List<PersonDto>,
    val crew: List<PersonDto>
)

fun CreditsDto.toModel(): CreditsModel = CreditsModel(
    actors = this.filterActors().toListOfPersonModel(),
    directors = this.filterDirectors().toListOfPersonModel()
)

private fun CreditsDto.filterActors(): List<PersonDto> {
    return this.cast.filter { person ->
        person.knownForDepartment == "Acting" && person.profilePath != null
    }
}

private fun CreditsDto.filterDirectors(): List<PersonDto> {
    val castDirectors = this.cast.filter { person ->
        person.knownForDepartment == "Directing" && person.profilePath != null
    }
    val crewDirectors = this.crew.filter { person ->
        person.knownForDepartment == "Directing" && person.profilePath != null
    }
    val directors: MutableList<PersonDto> = arrayListOf()
    directors.addAll(castDirectors)
    directors.addAll(crewDirectors)
    return directors.distinct().toList()
}