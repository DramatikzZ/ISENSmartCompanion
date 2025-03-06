package fr.isen.vincent.isensmartcompanion.utils.constants

import android.app.NotificationManager

object Constants {

    // MAIN SCREEN
    const val ERROR_MESSAGE_CHAT = "Veuillez entrer un message"
    const val PLACE_HOLDER_CHAT = "Écrivez un message..."

    // EVENTS SCREEN
    const val EVENT_TITLE = "Evènements"
    const val ERROR_MESSAGE_EVENTS = "Erreur lors du chargement des événements."
    const val NO_EVENTS = "Aucun événement disponible."

    // EVENTS DETAILS ACTIVITY SCREEN
    const val AUTHORIZED_MESSAGE = "Authorized !"
    const val REFUSED_MESSAGE = "Refused."
    const val SUBSCRIBE_BUTTON =  "S'abonner"
    const val UNSUBSCRIBE_BUTTON = "Se désabonner"

    // HISTORY SCREEN
    const val HISTORY_TITLE = "Historique des conversations"
    const val ERROR_MESSAGE_HISTORY = "Aucun historique disponible."
    const val DELETE_CHAT_MESSAGE = "Message supprimé"
    const val DELETE_HISTORY_MESSAGE = "Historique supprimé"
    const val DELETE_HISTORY = "Supprimer l'historique"



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