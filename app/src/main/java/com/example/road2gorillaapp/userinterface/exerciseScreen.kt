import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.road2gorillaapp.api.ExerciseDB
import org.json.JSONArray
import org.json.JSONObject

@Composable
fun ExerciseScreen(modifier: Modifier = Modifier) {
    var exercises by remember { mutableStateOf<List<JSONObject>>(emptyList()) }
    var bodyparts by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedBodyPart by remember { mutableStateOf<String?>(null) }

    // Fetch data on first render
    LaunchedEffect(true) {
        val exerciseResponse = ExerciseDB.getDataFromExerciseDB("https://exercisedb.p.rapidapi.com/exercises?limit=0")
        val bodyPartsResponse = ExerciseDB.getDataFromExerciseDB("https://exercisedb.p.rapidapi.com/exercises/bodyPartList")

        exercises = (exerciseResponse as JSONArray).let { jsonArray ->
            List(jsonArray.length()) { index -> jsonArray.getJSONObject(index) }
        }

        bodyparts = (bodyPartsResponse as JSONArray).let { jsonArray ->
            List(jsonArray.length()) { index -> jsonArray.getString(index) }
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        // Filter Dropdown
        BodyPartFilter(
            bodyparts = bodyparts,
            selectedBodyPart = selectedBodyPart,
            onBodyPartSelected = { selectedBodyPart = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Exercise List
        ExerciseList(
            exercises = exercises.filter { exercise ->
                selectedBodyPart == null || exercise.getString("bodyPart") == selectedBodyPart
            }
        )
    }
}

@Composable
fun BodyPartFilter(
    bodyparts: List<String>,
    selectedBodyPart: String?,
    onBodyPartSelected: (String?) -> Unit
) {
    var razsiri by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(onClick = { razsiri = true }, modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
            ) {
            Text(text = selectedBodyPart ?: "Select Body Part")
        }

        DropdownMenu(expanded = razsiri, onDismissRequest = { razsiri = false }) {
            DropdownMenuItem(onClick = { onBodyPartSelected(null); razsiri = false}, text = { Text("All") })
            bodyparts.forEach { bodyPart ->
                DropdownMenuItem(onClick = { onBodyPartSelected(bodyPart); razsiri = false },text = { Text(bodyPart) })
            }
        }
    }
}

@Composable
fun ExerciseList(exercises: List<JSONObject>) {
    LazyColumn {
        items(exercises) { exercise ->
            ExerciseItem(
                name = exercise.getString("name"),
                gifUrl = exercise.getString("gifUrl"),
                bodyPart = exercise.getString("bodyPart")
            )
        }
    }
}

@Composable
fun ExerciseItem(name: String, gifUrl: String, bodyPart: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(gifUrl),
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = name)
                Text(text = bodyPart)
            }
        }
    }
}

@Preview
@Composable
fun PreviewGoalsScreen() {
    ExerciseScreen()
}
