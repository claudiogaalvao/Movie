package com.claudiogalvaodev.moviemanager.ui.model

data class VideoModel(
    val id: String,
    val key: String,
    val name: String
) {
    fun getThumbnailUrl() = "https://img.youtube.com/vi/${key}/sddefault.jpg"
}