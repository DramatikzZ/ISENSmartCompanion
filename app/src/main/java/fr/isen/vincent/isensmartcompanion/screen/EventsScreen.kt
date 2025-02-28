package fr.isen.vincent.isensmartcompanion.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.ui.platform.LocalContext
import fr.isen.vincent.isensmartcompanion.models.EventModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import fr.isen.vincent.isensmartcompanion.api.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun EventsScreen(innerPadding: PaddingValues, eventHandler: (EventModel) -> Unit) {
    val context = LocalContext.current
    val events = remember { mutableStateOf<List<EventModel>>(listOf())}
    LaunchedEffect(Unit) {
        val call = NetworkManager.api.getEvents()
        call.enqueue(object : Callback<List<EventModel>> {
            override fun onResponse(
                p0: Call<List<EventModel>>,
                p1: Response<List<EventModel>>
            ) {
               events.value = p1.body() ?: listOf()
            }

            override fun onFailure(p0: Call<List<EventModel>>, p1: Throwable) {
                Log.e("request", p1.message ?:"request failed")
            }

        })
    }

    Column(modifier = Modifier.padding(innerPadding)) {
        LazyColumn {
            items(events.value) { event ->
                EventRow(event, eventHandler)
            }
        }
    }
}

@Composable
fun EventRow(event: EventModel, eventHandler: (EventModel) -> Unit) {
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .clickable {
            eventHandler(event)
        }
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
            .align(Alignment.CenterHorizontally)
        )
        {
            Text(event.title)
        }
    }
}