package com.claudiogalvaodev.moviemanager.ui.model

import com.claudiogalvaodev.moviemanager.data.bd.entity.ScheduledNotificationsEntity

data class ScheduledNotificationsModel(
    val id: Int,
    val movieId: Int,
    val movieName: String,
    val moviePosterPath: String,
    val remindAt: Long
)

fun ScheduledNotificationsModel.toEntity() = ScheduledNotificationsEntity(
    id = this.id,
    movieId = this.movieId,
    movieName = this.movieName,
    moviePosterPath = this.moviePosterPath,
    remindAt = this.remindAt
)