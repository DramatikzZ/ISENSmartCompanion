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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fr.isen.vincent.isensmartcompanion.R
import kotlinx.coroutines.launch
import fr.isen.vincent.isensmartcompanion.api.gemini.GeminiAPI
import fr.isen.vincent.isensmartcompanion.data.chat.ChatDatabase
import fr.isen.vincent.isensmartcompanion.models.ChatModel
import fr.isen.vincent.isensmartcompanion.utils.constants.Constants

@Composable
fun MainScreen(innerPadding: PaddingValues, chatHistory: ChatDatabase) {
    val context = LocalContext.current
    val userInput = remember { mutableStateOf("") }
    val savedInput = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val answerList = remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }
    val chatDao = chatHistory.ChatDao()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(R.drawable.isen_smart_companion_logo),
            contentDescription = context.getString(R.string.isen_logo),
            modifier = Modifier
                .size(240.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(answerList.value) { (question, response) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(16.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = "👤 : $question",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "🤖 : $response",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = userInput.value,
                onValueChange = { userInput.value = it },
                placeholder = { Text(Constants.PLACE_HOLDER_CHAT) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    if (userInput.value.isNotEmpty()) {
                        coroutineScope.launch {
                            savedInput.value = userInput.value
                            userInput.value = ""

                            val response = GeminiAPI.generateResponse(savedInput.value)
                            answerList.value += listOf(savedInput.value to response)

                            val chatMessage = ChatModel(question = savedInput.value, answer = response)
                            chatDao.insertChat(chatMessage)
                        }
                    } else {
                        Toast.makeText(context, Constants.ERROR_MESSAGE_CHAT, Toast.LENGTH_LONG).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.size(40.dp)
            ) {
                /*Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Envoyer",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxSize()

                )*/
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

