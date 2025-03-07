package fr.isen.vincent.isensmartcompanion.utils.constants

import android.app.NotificationManager

object Constants {

    // PART 3

    // RETROFIT
    const val EVENTS_ENDPOINT = "events.json"
    const val BASE_URL = "https://isen-smart-companion-default-rtdb.europe-west1.firebasedatabase.app/"

    // GEMINI
    const val GEMINI_MODEL_NAME = "gemini-1.5-flash"
    const val API_KEY = "AIzaSyBNw0r1o53RcwJa_-aLWOJUUEbyE03jL3E"

    // DATABASE
    const val DATABASE_NAME = "IsenSmartCompanion Database"



    // PART 4

    // CHANNEL NOTIFICATIONS
    const val CHANNEL_ID = "channel_id"
    const val CHANNEL_NAME = "channel_name"
    const val CHANNEL_DESCRIPTION = "Ceci est la description du channel"
    const val CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH

    // NOTIFICATIONS
    const val NOTIFICATION_MESSAGE = "N'oubliez pas d'être au rendez-vous !"
    const val NOTIFICATION_DELAY_MS = 10000 // temps en ms

}