package com.example.locationinbackground.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PointMap(@PrimaryKey val dateTime: String,
    val latitude: String,
    val location: String
)