package fr.isen.vincent.isensmartcompanion.chat_database

import android.content.Context
import androidx.room.Room

object DBInstance {
    @Volatile
    private var INSTANCE: ChatDatabase? = null
    private var chatDao: ChatDao? = null

    fun getDatabase(context: Context): ChatDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ChatDatabase::class.java,
                "IsenSmartCompanion-db"
            ).build()
            INSTANCE = instance
            chatDao = instance.ChatDao()
            instance
        }
    }

    fun getChatDao(context: Context): ChatDao {
        return chatDao ?: getDatabase(context).ChatDao()
    }
}