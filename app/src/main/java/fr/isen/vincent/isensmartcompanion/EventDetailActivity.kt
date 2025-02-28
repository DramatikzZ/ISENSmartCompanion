package fr.isen.vincent.isensmartcompanion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.isen.vincent.isensmartcompanion.models.EventModel
import fr.isen.vincent.isensmartcompanion.ui.theme.ISENSmartCompanionTheme



class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val event = intent.getSerializableExtra(EventDetailActivity.eventExtraKey) as? EventModel
        enableEdgeToEdge()
        setContent {
            ISENSmartCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if(event != null) {
                        EventDetail(event, innerPadding)
                    }
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("lifecycle", "EventDetailActivity onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "EventDetailActivity onResume")
    }

    override fun onStop() {
        Log.d("lifecycle", "EventDetailActivity onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("lifecycle", "EventDetailActivity onDestroy")
        super.onDestroy()
    }

    override fun onPause() {
        Log.d("lifecycle", "EventDetailActivity onPause")
        super.onPause()
    }

    companion object {
        val eventExtraKey = "eventExtraKey"
    }
}

@Composable
fun EventDetail(event: EventModel, innerPaddingValues: PaddingValues) {
    val context = LocalContext.current
    Column(Modifier.padding(innerPaddingValues)) {
        Button(
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            content = {
                Image(
                    painterResource(R.drawable.send),
                    contentDescription = "",
                    modifier = Modifier
                        .rotate(180f)
                        .size(30.dp)
                )
            }
        )
        Text(event.title)
        Text(event.date)
        Text(event.category)
        Text(event.location)
        Text(event.description)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ISENSmartCompanionTheme {
        EventDetail(EventModel.fakeEvents().first(), PaddingValues(8.dp))
    }
}