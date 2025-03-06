package fr.isen.vincent.isensmartcompanion.api.gemini

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import fr.isen.vincent.isensmartcompanion.utils.constants.Constants

object GeminiAPI {
    private val generativeModel = GenerativeModel(
        Constants.GEMINI_MODEL_NAME,
        Constants.API_KEY)

    suspend fun generateResponse(input: String): String {
        return try {
            val response = generativeModel.generateContent(input)
            response.text ?: "No response"
        } catch (e: Exception) {
            Log.e("GeminiAPI", "Error generating response: ${e.message}", e)
            "Error generating response: ${e.message}"
        }
    }
}