package fr.isen.vincent.isensmartcompanion.chat_database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.isen.vincent.isensmartcompanion.models.ChatModel

@Database(entities = [ChatModel::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun ChatDao(): ChatDao
}