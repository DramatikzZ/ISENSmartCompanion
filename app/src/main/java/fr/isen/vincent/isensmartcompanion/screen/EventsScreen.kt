package fr.isen.vincent.isensmartcompanion.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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


@Composable
fun EventsScreen(innerPadding: PaddingValues, eventHandler: (EventModel) -> Unit) {
    val context = LocalContext.current
    Column(modifier = Modifier.padding(innerPadding)) {
        val events = EventModel.fakeEvents()
        LazyColumn {
            items(events) { event ->
                EventRow(event, eventHandler)
            }
        }
    }
}

@Composable
fun EventRow(event: EventModel, eventHandler: (EventModel) -> Unit) {
    Card(modifier = Modifier
        .padding(16.dp)
        .clickable {
            eventHandler(event)
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(event.title)
        }
    }
}