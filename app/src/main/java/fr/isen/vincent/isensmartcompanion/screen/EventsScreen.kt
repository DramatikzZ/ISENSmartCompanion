package fr.isen.vincent.isensmartcompanion.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.ContextCompat.getString
import fr.isen.vincent.isensmartcompanion.R
import fr.isen.vincent.isensmartcompanion.models.EventModel
import fr.isen.vincent.isensmartcompanion.api.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun EventsScreen(innerPadding: PaddingValues, eventHandler: (EventModel) -> Unit) {
    val events = remember { mutableStateOf<List<EventModel>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val isError = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val call = NetworkManager.api.getEvents()
        call.enqueue(object : Callback<List<EventModel>> {
            override fun onResponse(call: Call<List<EventModel>>, response: Response<List<EventModel>>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    events.value = response.body() ?: emptyList()
                } else {
                    isError.value = true
                }
            }

            override fun onFailure(call: Call<List<EventModel>>, t: Throwable) {
                isLoading.value = false
                isError.value = true
                Log.e("request", t.message ?: "Request failed")
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = getString(context, R.string.event_title),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        when {
            isLoading.value -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
            isError.value -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = getString(context, R.string.error_message_events),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            events.value.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = getString(context, R.string.no_events),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(events.value) { event ->
                        EventRow(event, eventHandler)
                    }
                }
            }
        }
    }
}

@Composable
fun EventRow(event: EventModel, eventHandler: (EventModel) -> Unit) {
    val eventEmoji = getEventEmoji(event.category)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { eventHandler(event) }
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "$eventEmoji ${event.title}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

fun getEventEmoji(category: String?): String {
    return when (category?.lowercase()) {
        "vie associative" -> "🤝"
        "bde" -> "🎉"
        "bds" -> "⚽"
        "professionnel" -> "💼"
        "concours" -> "🏆"
        "institutionnel" -> "🏫"
        "technologique" -> "💻"
        "international" -> "✈️"
        else -> "📅"
    }
}
