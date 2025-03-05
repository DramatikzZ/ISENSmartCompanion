package fr.isen.vincent.isensmartcompanion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.vincent.isensmartcompanion.api.NetworkManager
import fr.isen.vincent.isensmartcompanion.chat_database.DBInstance
import fr.isen.vincent.isensmartcompanion.models.EventModel
import fr.isen.vincent.isensmartcompanion.screen.EventsScreen
import fr.isen.vincent.isensmartcompanion.screen.HistoryScreen
import fr.isen.vincent.isensmartcompanion.screen.MainScreen
import fr.isen.vincent.isensmartcompanion.screen.TabView
import fr.isen.vincent.isensmartcompanion.ui.theme.ISENSmartCompanionTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchEvents()
        Log.d("lifecycle", "MainActivity onCreate")
        enableEdgeToEdge()

        setContent {
            val homeTab = TabBarItem(title = getString(R.string.bottom_navbar_home), selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
            val eventsTab = TabBarItem(title = getString(R.string.bottom_navbar_event), selectedIcon = Icons.Filled.DateRange, unselectedIcon = Icons.Outlined.DateRange)
            val historyTab = TabBarItem(title = getString(R.string.bottom_navbar_history), selectedIcon = Icons.Filled.List, unselectedIcon = Icons.Outlined.List)

            // creating a list of all the tabs
            val tabBarItems = listOf(homeTab, eventsTab, historyTab)

            // creating our navController
            val navController = rememberNavController()

            // creating chatdao
            val chatHistory = DBInstance.getDatabase(applicationContext)

            ISENSmartCompanionTheme {
                Scaffold( bottomBar = {
                    TabView(tabBarItems, navController)
                },
                    modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(navController = navController, startDestination = homeTab.title) {
                        composable(homeTab.title) {
                            MainScreen(innerPadding, chatHistory)
                        }
                        composable(eventsTab.title) {
                            EventsScreen(
                                innerPadding = innerPadding,
                                eventHandler = { event ->
                                    startEventDetailActivity(event)
                                }
                            )
                        }
                        composable(historyTab.title) {
                            HistoryScreen(innerPadding, chatHistory)
                        }
                    }
                }
            }
        }
    }

    fun startEventDetailActivity(event: EventModel) {
        val intent = Intent(this, EventDetailActivity::class.java).apply {
            putExtra(EventDetailActivity.eventExtraKey, event)
        }
        startActivity(intent)
    }

    fun fetchEvents() {
        val call = NetworkManager.api.getEvents()
        call.enqueue(object : Callback<List<EventModel>> {
            override fun onResponse(p0: Call<List<EventModel>>, p1: Response<List<EventModel>>) {
                p1.body()?.forEach {
                    Log.d("request", "event : ${it.title}")
                }
            }

            override fun onFailure(p0: Call<List<EventModel>>, p1: Throwable) {
                Log.e("request", p1.message ?: "request failed")
            }
        })
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("lifecycle", "MainActivity onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "MainActivity onResume")
    }

    override fun onStop() {
        Log.d("lifecycle", "MainActivity onStop")
        super.onStop()
    }

    override fun onPause() {
        Log.d("lifecycle", "MainActivity onPause")
        super.onPause()
    }

    override fun onDestroy() {
        Log.d("lifecycle", "MainActivity onDestroy")
        super.onDestroy()
    }
}