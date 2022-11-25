package com.claudiogalvaodev.moviemanager.utils.notification.channels

import android.content.Context
import android.content.Intent
import android.os.Bundle

interface INotification {
    val id: Int
    val title: String
    val message: String

    fun getBundle(): Bundle
    fun getDestination(context: Context): Intent
}