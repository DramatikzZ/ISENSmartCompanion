package fr.isen.vincent.isensmartcompanion.data.chat

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.isen.vincent.isensmartcompanion.models.ChatModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    /*@Query("SELECT * FROM chat_messages WHERE id = :chatId")
    fun getChatById(chatId: Int): Flow<ChatModel>*/

    @Query("SELECT * FROM chat_messages ORDER BY timestamp DESC")
    fun getAllChats(): Flow<List<ChatModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chatMessage: ChatModel)

    @Delete
    suspend fun deleteChat(chatMessage: ChatModel)

    @Query("DELETE FROM chat_messages")
    suspend fun deleteAllChats()
}