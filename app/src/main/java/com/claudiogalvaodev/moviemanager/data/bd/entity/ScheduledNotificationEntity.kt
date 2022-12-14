package com.claudiogalvaodev.moviemanager.data.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.claudiogalvaodev.moviemanager.ui.model.ScheduledNotificationsModel

@Entity
data class ScheduledNotificationsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val movieId: Int,
    val movieName: String,
    val moviePosterPath: String,
    val remindAt: Long
)

fun ScheduledNotificationsEntity.toModel() = ScheduledNotificationsModel(
    id = this.id,
    movieId = this.movieId,
    movieName = this.movieName,
    moviePosterPath = this.moviePosterPath,
    remindAt = this.remindAt
)

fun List<ScheduledNotificationsEntity>.toListOfScheduledNotificationModel() = this.map {
    it.toModel()
}