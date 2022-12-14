package com.claudiogalvaodev.moviemanager.usecases.notification

import com.claudiogalvaodev.moviemanager.data.repository.IScheduledNotificationsRepository
import com.claudiogalvaodev.moviemanager.ui.model.MovieModel
import com.claudiogalvaodev.moviemanager.ui.model.ScheduledNotificationsModel
import com.claudiogalvaodev.moviemanager.utils.format.FormatUtils.convertToTimeInMillis
import com.claudiogalvaodev.moviemanager.utils.notification.ICineSeteNotificationManager
import com.claudiogalvaodev.moviemanager.utils.notification.channels.INotification
import com.claudiogalvaodev.moviemanager.utils.notification.notifications.MovieReleaseNotification

class ScheduleMovieReleaseNotificationUseCase(
    private val notificationManager: ICineSeteNotificationManager,
    private val notificationsRepository: IScheduledNotificationsRepository
) {

    suspend operator fun invoke(
        movie: MovieModel,
        notificationTitle: String,
        notificationDescription: String
    ) {
        val movieReleaseDate = movie.releaseDate ?: ""
        val notification = createNotification(movie.id, movieReleaseDate, notificationTitle, notificationDescription)
        scheduleNotification(notification, movieReleaseDate.convertToTimeInMillis())
        saveScheduledNotification(movie)
    }

    private fun createNotification(
        movieId: Int,
        movieReleaseDate: String,
        notificationTitle: String,
        notificationDescription: String
    ) = MovieReleaseNotification(
        id = movieId,
        title = notificationTitle,
        message = notificationDescription,
        movieId = movieId,
        movieRelease = movieReleaseDate
    )

    private fun scheduleNotification(notification: INotification, remindAt: Long) {
        notificationManager.scheduleNotification(
            timeInMillis = remindAt,
            notification = notification
        )
    }

    private suspend fun saveScheduledNotification(movie: MovieModel) {
        val movieReleaseDate = movie.releaseDate ?: ""
        notificationsRepository.save(ScheduledNotificationsModel(
            id = 0,
            movieId = movie.id,
            movieName = movie.title,
            moviePosterPath = movie.posterPath,
            remindAt = movieReleaseDate.convertToTimeInMillis()
        ))
    }
}