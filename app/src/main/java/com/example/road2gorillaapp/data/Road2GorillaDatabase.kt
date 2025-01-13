package com.example.road2gorillaapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Meal::class, TimeLog::class, Exercise::class], version = 1, exportSchema = false)
abstract class Road2GorillaDatabase2 : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun timeLogDao(): TimeLogDao
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: Road2GorillaDatabase2? = null

        fun getDatabase(context: Context): Road2GorillaDatabase2 {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, Road2GorillaDatabase2::class.java, "Road2GorillaDatabase2")
                    .build()
                    .also { INSTANCE = it }
            }

        }
    }
}
