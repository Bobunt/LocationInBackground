package com.example.locationinbackground.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PointMap(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val latitude: String,
    val location: String
)