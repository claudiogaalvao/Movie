package com.claudiogalvaodev.moviemanager.utils.notification.notifications

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.claudiogalvaodev.moviemanager.ui.moviedetails.MovieDetailsActivity
import com.claudiogalvaodev.moviemanager.utils.broadcasts.MovieReleaseBroadcastReceiver.Companion.ARGS_MOVIE_ID
import com.claudiogalvaodev.moviemanager.utils.broadcasts.MovieReleaseBroadcastReceiver.Companion.ARGS_MOVIE_RELEASE
import com.claudiogalvaodev.moviemanager.utils.broadcasts.MovieReleaseBroadcastReceiver.Companion.ARGS_NOTIFICATION_MESSAGE
import com.claudiogalvaodev.moviemanager.utils.broadcasts.MovieReleaseBroadcastReceiver.Companion.ARGS_NOTIFICATION_TITLE
import com.claudiogalvaodev.moviemanager.utils.notification.channels.INotification

class MovieReleaseNotification(
    override val id: Int,
    override val title: String,
    override val message: String,
    val movieId: Int,
    val movieRelease: String,
): INotification {
    override fun getBundle() = Bundle().apply {
        putInt(ARGS_MOVIE_ID, movieId)
        putString(ARGS_MOVIE_RELEASE, movieRelease)
        putString(ARGS_NOTIFICATION_TITLE, title)
        putString(ARGS_NOTIFICATION_MESSAGE, message)
    }

    override fun getDestination(context: Context): Intent {
        return MovieDetailsActivity.newInstance(context, movieId, movieRelease)
    }
}