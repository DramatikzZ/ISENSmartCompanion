package fr.isen.vincent.isensmartcompanion.data.chat

import android.content.Context
import androidx.room.Room
import fr.isen.vincent.isensmartcompanion.utils.constants.Constants

object DBInstance {
    @Volatile
    private var INSTANCE: ChatDatabase? = null

    fun getDatabase(context: Context): ChatDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ChatDatabase::class.java,
                Constants.DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            instance
        }
    }

    fun getChatDao(context: Context): ChatDao {
        return getDatabase(context).ChatDao()
    }
}
