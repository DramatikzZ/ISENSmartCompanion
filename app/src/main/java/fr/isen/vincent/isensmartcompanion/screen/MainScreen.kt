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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import fr.isen.vincent.isensmartcompanion.R
import kotlinx.coroutines.launch
import fr.isen.vincent.isensmartcompanion.api.gemini.GeminiAPI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(innerPadding : PaddingValues) {
    val context = LocalContext.current
    var userInput = remember { mutableStateOf("") }

    var savedinput = remember { mutableStateOf("") }
    var answer = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    var AnswerList = remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxSize().padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween)
    {
        Image(
            painterResource(R.drawable.isen_logo),
            context.getString(R.string.isen_logo))

        Text(
            text = context.getString(R.string.app_title),
            modifier = Modifier
        )

        Text("",
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5F)
        )

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)
            .padding(8.dp)
            .height(60.dp)
        )
        {
            TextField(
                value = userInput.value,
                onValueChange = {userInput.value = it},
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
                            userInput.value = ""
                            answer.value = GeminiAPI.generateResponse(savedinput.value)
                            Toast.makeText(context, answer.value, Toast.LENGTH_LONG).show()
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