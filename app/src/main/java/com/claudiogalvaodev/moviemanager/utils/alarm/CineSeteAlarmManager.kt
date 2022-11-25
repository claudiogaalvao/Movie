package com.claudiogalvaodev.moviemanager.utils.alarm

import android.app.AlarmManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.claudiogalvaodev.moviemanager.R

class CineSeteAlarmManager(
    private val context: Context
) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun hasPermission(): Boolean = alarmManager.canScheduleExactAlarms()

    fun scheduleNotification() {
        if (hasPermission()) {

        } else {
            askForPermission()
        }
    }

    private fun askForPermission() {
        AlertDialog.Builder(context)
            .setTitle(R.string.bottomsheet_title)
            .setMessage(R.string.my_lists_form_new_instruction)
            .setPositiveButton(R.string.new_list_dialog_button) { _, _ ->
                goToSettings()
            }
            .setNegativeButton(R.string.filter_reset) { _, _ ->

            }
            .setCancelable(false)
            .show()

    }

    private fun goToSettings() {
        val intent = Intent().apply {
            action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
        }
        context.startActivity(intent)
    }

}