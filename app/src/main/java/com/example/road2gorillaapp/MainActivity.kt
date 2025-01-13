package com.example.road2gorillaapp

//database
import com.example.road2gorillaapp.data.Road2GorillaDatabase2

import com.example.road2gorillaapp.userinterface.HomeScreen
import com.example.road2gorillaapp.userinterface.MealPrepScreen
import com.example.road2gorillaapp.userinterface.ProfileScreen
import com.example.road2gorillaapp.userinterface.StatsScreen
import ExerciseScreen
import android.media.MediaPlayer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import com.example.road2gorillaapp.ui.theme.Road2GorillaAppTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.QueryStats

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MyActivity"
    }

    private lateinit var databaseski: Road2GorillaDatabase2
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databaseski = Road2GorillaDatabase2.getDatabase(this)


        enableEdgeToEdge()
        setContent {
            Road2GorillaAppTheme {
                MainScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Start")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Resume")
        playSound(R.raw.resume)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Pause")
        playSound(R.raw.pause)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "Restart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Destroy")
        mediaPlayer?.release()
    }

    private fun playSound(resourceId: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, resourceId)
        mediaPlayer?.start()
    }
}

@Composable
fun MainScreen() {
    var trenutniScreen by remember { mutableStateOf(2) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(trenutniScreen) { trenutniScreen = it }
        }
    ) { innerPadding ->
        when (trenutniScreen) {
            0 -> ExerciseScreen(modifier = Modifier.padding(innerPadding))
            1 -> StatsScreen(modifier = Modifier.padding(innerPadding))
            2 -> HomeScreen(modifier = Modifier.padding(innerPadding))
            3 -> MealPrepScreen(modifier = Modifier.padding(innerPadding))
            4 -> ProfileScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun BottomNavigationBar(trenutniScreen: Int, izbraniScreen: (Int) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.FitnessCenter, contentDescription = "Exercises") },
            label = { Text("EXERCISE") },
            selected = trenutniScreen == 0,
            onClick = { izbraniScreen(0) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.QueryStats, contentDescription = "Stats") },
            label = { Text("STATS") },
            selected = trenutniScreen == 1,
            onClick = { izbraniScreen(1) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("HOME") },
            selected = trenutniScreen == 2,
            onClick = { izbraniScreen(2) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.RestaurantMenu, contentDescription = "Meal Prep") },
            label = { Text("MEALS") },
            selected = trenutniScreen == 3,
            onClick = { izbraniScreen(3) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("PROFILE") },
            selected = trenutniScreen == 4,
            onClick = { izbraniScreen(4) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    Road2GorillaAppTheme {
        MainScreen()
    }
}
