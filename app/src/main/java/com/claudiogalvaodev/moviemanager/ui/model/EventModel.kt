package com.claudiogalvaodev.moviemanager.ui.model

data class EventModel(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val eventDate: String,
    val startAt: String,
    val finishAt: String
)

