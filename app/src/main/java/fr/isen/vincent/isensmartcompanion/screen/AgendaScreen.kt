package fr.isen.vincent.isensmartcompanion.screen

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import fr.isen.vincent.isensmartcompanion.R
import fr.isen.vincent.isensmartcompanion.api.NetworkManager
import fr.isen.vincent.isensmartcompanion.models.CourseGenerator
import fr.isen.vincent.isensmartcompanion.models.CourseModel
import fr.isen.vincent.isensmartcompanion.models.EventModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AgendaScreen() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val courses = CourseGenerator.exampleCourses()
    val filteredCourses = courses.filter { it.dayOfWeek == selectedDate.dayOfWeek.value }
    val dayName = selectedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.FRENCH)
    val context = LocalContext.current
    val formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH))
    val events = remember { mutableStateOf<List<EventModel>>(emptyList()) }
    val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.event_preferences), Context.MODE_PRIVATE
    )
    val isLoading = remember { mutableStateOf(true) }
    val isError = remember { mutableStateOf(false) }

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

    val filteredEvents = events.value.filter { eventModel ->
        val isFavorite = sharedPreferences.getBoolean(eventModel.title, false)
        val isSameDay = eventModel.date == formattedDate // Vérification par date
        isFavorite && isSameDay
    }

    filteredEvents.forEach { event ->
        Log.d("FilteredEvents", "Event Title: ${event.title}")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column (modifier = Modifier.padding(top = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally){
                        Text(getString(context, R.string.agenda_title),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { selectedDate = selectedDate.minusDays(1) }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = getString(context, R.string.previous_day))
                            }
                            Text(text = "$dayName $formattedDate", style = MaterialTheme.typography.titleMedium)
                            IconButton(onClick = { selectedDate = selectedDate.plusDays(1) }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = getString(context, R.string.next_day))
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (filteredCourses.isEmpty()) {
                Text(
                    text = getString(context, R.string.no_courses),
                    modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    items(filteredCourses) { course ->
                        CourseItem(course)
                    }
                    items(filteredEvents) { event ->
                        EventItem(event)
                    }
                }
            }
        }
    }
}

@Composable
fun CourseItem(course: CourseModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = course.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "${course.startTime} - ${course.endTime}")
            Text(text = "Location: ${course.location}")
            Text(text = "Professeur: ${course.instructor}")
        }
    }
}

@Composable
fun EventItem(event: EventModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Location: ${event.location}")
            Text(text = "Description: ${event.description}")
        }
    }
}