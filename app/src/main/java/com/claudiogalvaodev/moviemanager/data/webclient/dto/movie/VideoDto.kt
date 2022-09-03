package com.claudiogalvaodev.moviemanager.data.webclient.dto.movie

import com.claudiogalvaodev.moviemanager.ui.model.VideoModel
import com.google.gson.annotations.SerializedName

data class VideoDto(
    val id: String,
    val key: String,
    val name: String,
    @SerializedName("iso_3166_1")
    val region: String,
    @SerializedName("iso_639_1")
    val language: String,
    val official: Boolean,
    @SerializedName("published_at")
    val publishedAt: String,
    val site: String,
    val size: Int,
    val type: String
)

fun VideoDto.toModel(): VideoModel = VideoModel(
    id = this.id,
    key = this.key,
    name = this.name
)

fun List<VideoDto>?.toListOfVideosModel(): List<VideoModel> = this?.let { videos ->
    videos.map { videoDto -> videoDto.toModel() }
} ?: emptyList()