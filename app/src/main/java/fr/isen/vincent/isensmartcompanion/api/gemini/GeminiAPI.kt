package fr.isen.vincent.isensmartcompanion.api.gemini

import com.google.ai.client.generativeai.GenerativeModel

object GeminiAPI {
    val generativeModel = GenerativeModel("gemini-1.5-flash", "AIzaSyBNw0r1o53RcwJa_-aLWOJUUEbyE03jL3E")

    suspend fun generateResponse(input: String): String {
        return try {
            val response = generativeModel.generateContent(input)
            response.text ?: "No response"
        } catch (e: Exception) {
            "Error generating response: ${e.message}"
        }
    }
}