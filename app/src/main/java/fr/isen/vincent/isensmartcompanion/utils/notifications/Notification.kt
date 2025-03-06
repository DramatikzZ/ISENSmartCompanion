package fr.isen.vincent.isensmartcompanion.utils.notifications

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import fr.isen.vincent.isensmartcompanion.models.EventModel
import fr.isen.vincent.isensmartcompanion.utils.constants.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Notification {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun pushNotifications(
        context: Context,
        event: EventModel,
        permissionLauncher: ManagedActivityResultLauncher<String, Boolean>
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            return
        }

        val notification = NotificationBuilder.buildNotification(context, event)

        CoroutineScope(Dispatchers.Main).launch {
            delay(Constants.NOTIFICATION_DELAY_MS.toLong())
            NotificationManagerCompat.from(context).notify(System.currentTimeMillis().toInt(), notification)
        }

    }
}