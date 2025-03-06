package fr.isen.vincent.isensmartcompanion.utils.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import fr.isen.vincent.isensmartcompanion.utils.constants.Constants

object NotificationChannelManager {
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                Constants.CHANNEL_ID,
                Constants.CHANNEL_NAME,
                Constants.CHANNEL_IMPORTANCE
            ).apply {
                description = Constants.CHANNEL_DESCRIPTION
            }

            val notificationManager: NotificationManager =
                context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}