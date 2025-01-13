package com.example.road2gorillaapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal): Long

    @Query("SELECT * FROM Meal")
    suspend fun getAllMeals(): List<Meal>

    @Delete
    suspend fun deleteMeal(meal: Meal)
}

@Dao
interface TimeLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeLog(timeLog: TimeLog): Long
}

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise): Long

    @Query("SELECT * FROM Exercise")
    suspend fun getAllExercises(): List<Exercise>
}
