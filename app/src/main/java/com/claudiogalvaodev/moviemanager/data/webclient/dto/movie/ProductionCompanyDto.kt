package com.claudiogalvaodev.moviemanager.data.webclient.dto.movie

import com.claudiogalvaodev.moviemanager.ui.model.ProductionCompanyModel
import com.google.gson.annotations.SerializedName

data class ProductionCompanyDto(
    val id: Int,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String,
    @SerializedName("logo_path")
    val logoPath: String?
)

fun ProductionCompanyDto.toModel() = ProductionCompanyModel(
    id = this.id,
    name = this.name,
    originCountry = this.originCountry,
    logoPath = this.logoPath ?: ""
)

fun List<ProductionCompanyDto>.toListOfProductionCompanyModel(): List<ProductionCompanyModel> = this
    .filter { company -> !company.logoPath.isNullOrBlank() }
    .map { it.toModel() }
