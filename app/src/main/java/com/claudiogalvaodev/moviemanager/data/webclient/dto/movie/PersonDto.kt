package com.claudiogalvaodev.moviemanager.data.webclient.dto.movie

import com.claudiogalvaodev.moviemanager.ui.model.PersonModel
import com.google.gson.annotations.SerializedName

data class PersonDto(
    val id: Int,
    val adult: Boolean?,
    val gender: Int?,
    @SerializedName("known_for_department")
    val knownForDepartment: String?,
    @SerializedName("known_for")
    val knownFor: List<MovieDto>?,
    val name: String,
    @SerializedName("original_name")
    val originalName: String?,
    val popularity: Double?,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("cast_id")
    val castId: Int?,
    val character: String?,
    @SerializedName("credit_id")
    val creditId: String?,
    val birthday: String?,
    val deathday: String?,
    @SerializedName("place_of_birth")
    val placeOfBirth: String?,
    val biography: String?,
    val homepage: String?,
    val job: String?,
    val department: String?,
    val order: Int?
)

fun PersonDto.toModel(): PersonModel = PersonModel(
    id = this.id,
    adult = this.adult ?: false,
    gender = this.gender ?: 0,
    knownForDepartment = this.knownForDepartment ?: "",
    knownFor = this.knownFor.toListOfMoviesModel(),
    name = this.name,
    originalName = this.originalName ?: "",
    popularity = this.popularity ?: 0.0,
    profilePath = this.profilePath,
    castId = this.castId ?: 0,
    character = this.character ?: "",
    creditId = this.creditId ?: "",
    birthday = this.birthday,
    deathday = this.deathday,
    placeOfBirth = this.placeOfBirth,
    biography = this.biography ?: "",
    homepage = this.homepage,
    job = this.job,
    department = this.department,
    order = this.order
)

fun List<PersonDto>?.toListOfPersonModel(): List<PersonModel> = this?.let { people ->
    people
        .filter { personDto -> personDto.profilePath.isNullOrBlank().not() }
        .map { personDto -> personDto.toModel() }
} ?: emptyList()