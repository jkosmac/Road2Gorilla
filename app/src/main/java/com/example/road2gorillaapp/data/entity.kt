package com.example.road2gorillaapp.data

import androidx.room.*

@Entity(tableName = "Meal")
data class Meal(
    @PrimaryKey(autoGenerate = true) val Id: Int = 0,
    val name: String,
    val calories: Int,
    val category: String,
)

@Entity(tableName = "TimeLog")
data class TimeLog(
    @PrimaryKey(autoGenerate = true) val timeLogId: Int = 0,
    val startTime: Long,
    val endTime: Long?,
    val duration: Long,
)

@Entity(tableName = "Exercise")
data class Exercise(
    @PrimaryKey(autoGenerate = true) val exerciseId: Int = 0,
    val name: String,
    val currentWeight: Double,
    val currentUnit: String,
    val targetWeight: Double,
    val targetUnit: String,
)

