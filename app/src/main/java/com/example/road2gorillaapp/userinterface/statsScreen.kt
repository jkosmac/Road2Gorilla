package com.example.road2gorillaapp.userinterface
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.road2gorillaapp.data.Exercise
import kotlinx.coroutines.launch
import com.example.road2gorillaapp.data.Road2GorillaDatabase2

@Composable
fun StatsScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val database = remember { Road2GorillaDatabase2.getDatabase(context) }

    val exerciseDao = database.exerciseDao()
    val coroutineScope = rememberCoroutineScope()

    var exerciseName by remember { mutableStateOf("") }
    var currentWeight by remember { mutableStateOf("") }
    var currentUnit by remember { mutableStateOf("kg") }
    var targetWeight by remember { mutableStateOf("") }
    var targetUnit by remember { mutableStateOf("kg") }

    val exercises by produceState(initialValue = listOf<Exercise>(), exerciseDao) {
        value = exerciseDao.getAllExercises()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Track Your Exercise", style = MaterialTheme.typography.headlineMedium)

        TextField(
            value = exerciseName,
            onValueChange = { exerciseName = it },
            label = { Text("Exercise Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = currentWeight,
                onValueChange = { currentWeight = it },
                label = { Text("Current Weight") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            Text(currentUnit, modifier = Modifier.padding(start = 8.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = targetWeight,
                onValueChange = { targetWeight = it },
                label = { Text("Target Weight") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            Text(targetUnit, modifier = Modifier.padding(start = 8.dp))
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    val exercise = Exercise(
                        name = exerciseName,
                        currentWeight = currentWeight.toDoubleOrNull() ?: 0.0,
                        currentUnit = currentUnit,
                        targetWeight = targetWeight.toDoubleOrNull() ?: 0.0,
                        targetUnit = targetUnit
                    )
                    exerciseDao.insertExercise(exercise)
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Saved Exercises", style = MaterialTheme.typography.headlineSmall)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Omogoča raztezanje in drsenje v stolpcu
        ) {
            items(exercises) { exercise ->
                ExerciseItem(exercise)
            }
        }
    }
}

@Composable
fun ExerciseItem(exercise: Exercise) {
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
            Column {
                Text(text = exercise.name, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Current: ${exercise.currentWeight} ${exercise.currentUnit}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Target: ${exercise.targetWeight} ${exercise.targetUnit}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
