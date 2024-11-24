package com.example.road2gorillaapp

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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

//profile
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.road2gorillaapp.R

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MyActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Pause")
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
            0 -> GoalsScreen(modifier = Modifier.padding(innerPadding))
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
            icon = { Icon(Icons.Default.Flag, contentDescription = "Goals") },
            label = { Text("GOALS") },
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

@Composable
fun GoalsScreen(modifier: Modifier = Modifier) {
    Text("Goals Screen", modifier = modifier)
}

@Composable
fun StatsScreen(modifier: Modifier = Modifier) {
    Text("Stats Screen", modifier = modifier)
}
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var casovnik by remember { mutableStateOf(0L) }
    var poteka by remember { mutableStateOf(false) }

    LaunchedEffect(poteka) {
        if (poteka) {
            while (poteka) {
                kotlinx.coroutines.delay(1000L)
                casovnik++
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top // Postavitev vseh elementov na vrh
    ) {
        // naslov
        Text(
            text = "Let's Get Bigger!",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        // stoparica
        Card(
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Stopwatch",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = formatiranjeCasa(casovnik),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // gumbi
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // start/pause
            Button(
                onClick = { poteka = !poteka }
            ) {
                Text(if (poteka) "Pause" else "Start")
            }

            // resetiranje
            Button(
                onClick = {
                    poteka = false
                    casovnik = 0L
                }
            ) {
                Text("Reset")
            }
        }
    }
}

// formatiranje časa
fun formatiranjeCasa(casVSekundah: Long): String {
    val ura = casVSekundah / 3600
    val minuta = (casVSekundah % 3600) / 60
    val sekunda = casVSekundah % 60
    return String.format("%02d:%02d:%02d", ura, minuta, sekunda)
}



data class Meal(val ime: String, val kalorije: Int, val kategorija: String)

@Composable
fun MealPrepScreen(modifier: Modifier = Modifier) {
    var mealIme by remember { mutableStateOf("") }
    var kalorije by remember { mutableStateOf("") }
    var izbranaKategorija by remember { mutableStateOf("Breakfast") }
    var obroki by remember { mutableStateOf(listOf<Meal>()) }

    val kategorije = listOf("Breakfast", "Lunch", "Dinner", "Snack")

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            OutlinedTextField(
                value = mealIme,
                onValueChange = { mealIme = it },
                label = { Text("Meal Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = kalorije,
                onValueChange = { kalorije = it },
                label = { Text("Calories") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            var razsiri by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = izbranaKategorija,
                    onValueChange = {},
                    label = { Text("Category") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { razsiri = !razsiri }) {
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Select Category")
                        }
                    }
                )
                DropdownMenu(
                    expanded = razsiri,
                    onDismissRequest = { razsiri = false }
                ) {
                    kategorije.forEach { kategorija ->
                        DropdownMenuItem(
                            text = { Text(kategorija) },
                            onClick = {
                                izbranaKategorija = kategorija
                                razsiri = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            kategorije.forEach { kategorija ->
                if (obroki.any { it.kategorija == kategorija }) {
                    var kategorijeRazsiri by remember { mutableStateOf(false) }
                    TextButton(onClick = { kategorijeRazsiri = !kategorijeRazsiri }) {
                        Text(kategorija, style = MaterialTheme.typography.titleLarge)
                    }
                    if (kategorijeRazsiri) {
                        obroki.filter { it.kategorija == kategorija }.forEach { meal ->
                            MealItem(meal)
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                if (mealIme.isNotBlank() && kalorije.isNotBlank()) {
                    obroki = obroki + Meal(mealIme, kalorije.toInt(), izbranaKategorija)
                    mealIme = ""
                    kalorije = ""
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("Add Meal")
        }
    }
}

@Composable
fun MealItem(meal: Meal) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = meal.ime, style = MaterialTheme.typography.bodyMedium)
            Text(text = "${meal.kalorije} kcal", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    // spremenljivka za profilno sliko
    var profileSlika: Painter = painterResource(id = R.drawable.gorillapfp)

    // profil
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // profilna slika
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.LightGray, shape = CircleShape)
                .clickable {
                    // dodajanje nove slike ..
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = profileSlika,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(110.dp)
                    .background(Color.White, shape = CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // username
        Text(text = "HRIBCEK NA TRENU", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(24.dp))

        // drugo
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Column(
                horizontalAlignment = Alignment.Start,
                //verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Friends", fontSize = 18.sp)
                Text(text = "Weight:", fontSize = 18.sp)
                Text(text = "Height:", fontSize = 18.sp)
            }

            Column(
                horizontalAlignment = Alignment.Start,
                //verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "(3)", fontSize = 18.sp)
                Text(text = "100 kg", fontSize = 18.sp)
                Text(text = "175 cm", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Sign out in Delete Account gumba
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    // odjava
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 4.dp)
            ) {
                Text("Sign Out", color = Color.White)
            }

            Button(
                onClick = {
                    // brisanje računa
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 4.dp)
            ) {
                Text("Delete Account", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    Road2GorillaAppTheme {
        MainScreen()
    }
}
