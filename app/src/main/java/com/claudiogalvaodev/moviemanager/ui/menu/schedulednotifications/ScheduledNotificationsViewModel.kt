package com.claudiogalvaodev.moviemanager.ui.menu.schedulednotifications

import androidx.lifecycle.ViewModel
import com.claudiogalvaodev.moviemanager.ui.model.ScheduledNotificationsModel
import com.claudiogalvaodev.moviemanager.usecases.notification.GetAllScheduledNotification
import kotlinx.coroutines.flow.Flow

class ScheduledNotificationsViewModel(
    private val getAllScheduledNotification: GetAllScheduledNotification
): ViewModel() {

    val allScheduledNotificationResult: Flow<Result<List<ScheduledNotificationsModel>>> =
        getAllScheduledNotification()

}