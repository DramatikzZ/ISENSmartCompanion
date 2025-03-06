package fr.isen.vincent.isensmartcompanion.utils.notifications

import android.content.Context
import androidx.core.app.NotificationCompat
import fr.isen.vincent.isensmartcompanion.R
import fr.isen.vincent.isensmartcompanion.models.EventModel
import fr.isen.vincent.isensmartcompanion.utils.constants.Constants

object NotificationBuilder {
    fun buildNotification(context: Context, event: EventModel): android.app.Notification {
        return NotificationCompat.Builder(context, Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(event.title)
            .setContentText(Constants.NOTIFICATION_MESSAGE)
            /*.setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(0, 500, 250, 500))
            .setDefaults(NotificationCompat.DEFAULT_ALL)*/
            .build()
    }
}