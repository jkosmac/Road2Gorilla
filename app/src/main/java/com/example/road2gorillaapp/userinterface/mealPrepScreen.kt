package com.example.road2gorillaapp.userinterface

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.road2gorillaapp.data.Meal
import com.example.road2gorillaapp.data.Road2GorillaDatabase2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun MealPrepScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val database = remember { Road2GorillaDatabase2.getDatabase(context) }
    val mealDao = remember { database.mealDao() }

    var mealIme by remember { mutableStateOf("") }
    var kalorije by remember { mutableStateOf("") }
    var izbranaKategorija by remember { mutableStateOf("Breakfast") }
    var obroki by remember { mutableStateOf(listOf<Meal>()) }

    val kategorije = listOf("Breakfast", "Lunch", "Dinner", "Snack")

    // Fetch meals from the database
    LaunchedEffect(Unit) {
        obroki = mealDao.getAllMeals()
    }

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

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                kategorije.forEach { kategorija ->
                    val mealsByCategory = obroki.filter { it.category == kategorija }
                    if (mealsByCategory.isNotEmpty()) {
                        item {
                            Text(
                                kategorija,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                        items(mealsByCategory) { meal ->
                            MealItem(meal)
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                if (mealIme.isNotBlank() && kalorije.isNotBlank()) {
                    val newMeal = Meal(name = mealIme, calories = kalorije.toInt(), category = izbranaKategorija)
                    GlobalScope.launch(Dispatchers.IO) {
                        mealDao.insertMeal(newMeal)
                        obroki = mealDao.getAllMeals()
                    }
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
            Text(text = meal.name, style = MaterialTheme.typography.bodyMedium)
            Text(text = "${meal.calories} kcal", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
