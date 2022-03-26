package com.claudiogalvaodev.moviemanager.data.model

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val eventDate: String,
    val startAt: String,
    val finishAt: String
)

