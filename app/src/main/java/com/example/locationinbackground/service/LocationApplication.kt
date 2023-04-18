package com.example.locationinbackground.service

import android.app.Application
import com.example.locationinbackground.data.DataBase

class LocationApplication: Application() {
    val database: DataBase by lazy { DataBase.getDatabase(this) }
}