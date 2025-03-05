package fr.isen.vincent.isensmartcompanion.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.ai.client.generativeai.Chat
import fr.isen.vincent.isensmartcompanion.R
import kotlinx.coroutines.launch
import fr.isen.vincent.isensmartcompanion.api.gemini.GeminiAPI
import fr.isen.vincent.isensmartcompanion.chat_database.ChatDao
import fr.isen.vincent.isensmartcompanion.chat_database.ChatDatabase
import fr.isen.vincent.isensmartcompanion.models.ChatModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(innerPadding: PaddingValues, chatHistory: ChatDatabase) {
    val context = LocalContext.current
    var userInput = remember { mutableStateOf("") }

    var savedinput = remember { mutableStateOf("") }
    var answer = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    // Liste pour stocker les messages envoyés et leurs réponses
    var AnswerList = remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }

    val chatDao = chatHistory.ChatDao()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // Image et titre
        Image(
            painterResource(R.drawable.isen_logo),
            context.getString(R.string.isen_logo),
            modifier = Modifier
                .fillMaxWidth() // L'image prendra toute la largeur
                .padding(horizontal = 16.dp) // Appliquez un padding horizontal de 32 dp
                .height(200.dp) // Définissez une hauteur pour l'image
        )

        Text(
            text = context.getString(R.string.app_title),
            modifier = Modifier
        )

        // Espacement entre le titre et la zone de texte
        Spacer(modifier = Modifier.height(10.dp))

        // LazyColumn pour afficher les messages envoyés et les réponses
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Permet à la liste de prendre l’espace disponible
                .padding(horizontal = 24.dp)
                .padding(vertical = 8.dp)
        ) {
            items(AnswerList.value) { (question, response) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(colorResource(id = R.color.chat), RoundedCornerShape(16.dp))
                        .padding(8.dp)
                ) {
                    Text(text = "Vous: $question",
                        color = colorResource(id = R.color.isen_red),
                        fontWeight = FontWeight.Bold)
                    //Text("")
                    Text(text = "Bot: $response",
                        color = Color.Black)
                }
            }
        }

        // Espace entre la liste de messages et le champ de texte
        Spacer(modifier = Modifier.height(10.dp))

        // Zone de saisie et bouton pour envoyer la question
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
                .padding(8.dp)
                .height(60.dp)
        ) {
            TextField(
                value = userInput.value,
                onValueChange = { userInput.value = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorContainerColor = Color.Transparent
                ),
                modifier = Modifier.weight(1F)
            )

            Button(
                onClick = {
                    if (userInput.value.isNotEmpty()) {
                        coroutineScope.launch {
                            savedinput.value = userInput.value
                            userInput.value = "" // Réinitialisation du champ de texte

                            // Appel à l'API pour générer la réponse
                            val response = GeminiAPI.generateResponse(savedinput.value)

                            // Mise à jour de la liste des réponses
                            AnswerList.value = AnswerList.value + listOf(savedinput.value to response)

                            val chatMessage = ChatModel(question = savedinput.value, answer = response)
                            chatDao.insertChat(chatMessage)

                            // Affichage d'un toast avec la réponse (facultatif)
                            Toast.makeText(context, response, Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(context, "Please enter text", Toast.LENGTH_LONG).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                content = {
                    Image(
                        painterResource(R.drawable.send),
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}
