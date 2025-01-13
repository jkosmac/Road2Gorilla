package com.example.road2gorillaapp.userinterface

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.json.JSONArray
import com.example.road2gorillaapp.api.SportAPI
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: StoparicaViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val casovnik by viewModel.casovnik
    val poteka by viewModel.poteka

    // Stanje za izbran šport in vidnost DropdownMenu
    var izbranSport by remember { mutableStateOf<String?>(null) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Let's Get Bigger!",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (poteka) {
                        viewModel.pauseStoparica()
                    } else {
                        viewModel.startStoparica()
                    }
                }
            ) {
                Text(if (poteka) "Pause" else "Start")
            }

            Button(
                onClick = { viewModel.resetStoparica() }
            ) {
                Text("Reset")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // DropdownMenu za izbiro športa
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            OutlinedButton(onClick = { dropdownExpanded = true }, modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
            ){
                Text(izbranSport?.capitalize() ?: "Choose Sport")
            }

            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false },
                modifier = Modifier.align(Alignment.Center)
            ) {
                val sports = listOf("football", "basketball", "tennis", "volleyball", "handball")
                sports.forEach { sport ->
                    DropdownMenuItem(
                        text = { Text(sport.capitalize()) },
                        onClick = {
                            izbranSport = sport
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Prikaz športnih tekem glede na izbran šport
        izbranSport?.let {
            sportsTekme(it)
        }
    }
}



@Composable
fun DisplayJSONData(data: JSONArray?) {
    if (data == null) {
        Text("Loading data...", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    } else {
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            items((0 until data.length()).toList()) { index ->
                val item = data.getJSONObject(index)
                val homeTeam = item.getJSONObject("homeTeam").getString("name")
                val awayTeam = item.getJSONObject("awayTeam").getString("name")
                val timestamp = item.getLong("startTimestamp")

                // Varno pridobivanje winnerCode
                val winnerCode = if (item.has("winnerCode")) item.getInt("winnerCode") else -1
                val winnerTeam = when (winnerCode) {
                    1 -> homeTeam
                    2 -> awayTeam
                    else -> "DRAW"
                }

                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "$homeTeam vs $awayTeam",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        )
                        Text(
                            text = "Winner: $winnerTeam",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            text = "Start time: ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Date(timestamp * 1000))}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
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


@Composable
fun sportsTekme(izbranSport : String?){
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val current = LocalDateTime.now().format(formatter)
    var sportsResponse by remember { mutableStateOf<JSONArray?>(null) }


    LaunchedEffect(izbranSport) {
        sportsResponse = SportAPI.getDataFromSportAPI("https://sportapi7.p.rapidapi.com/api/v1/sport/$izbranSport/scheduled-events/$current") as JSONArray?
        Log.d("anze", sportsResponse.toString())
    }

    Text(
        text = "${izbranSport?.capitalize()} Matches",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )


    DisplayJSONData(sportsResponse)
}