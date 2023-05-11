package com.example.locationinbackground.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.os.IBinder
import android.os.Handler
import android.util.Log
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.locationinbackground.R
import com.example.locationinbackground.data.DataBase
import com.example.locationinbackground.data.PointMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import kotlin.coroutines.CoroutineContext

class LocationService() : Service(), LocationListener, CoroutineScope{
    private val job: Job = Job()
    private lateinit var database: DataBase
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job
    private lateinit var locationManager: LocationManager
    private var routeUser = arrayListOf<PointMap>()
    private val CHANNEL_ID = "ForegroundService Location"

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // Send a notification that service is started

        database = DataBase.getDatabase(this)
        clearDB()
        Log.v("infoService","Service started.")
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val input = intent?.getStringExtra("inputExtra")

        createNotificationChannel()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service Kotlin Example")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_baseline_edit_location_24)
            .build()

        startForeground(1, notification)
        showLocater()
        return START_STICKY
    }

    private fun clearDB() = launch{
        database.clearAllTables()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("infoService","Service destroyed.")
        insertLocation()
    }

    private fun showLocater() {
        getLocate()
    }

    @SuppressLint("MissingPermission")
    private fun getLocate(){
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5f, this)
    }

    private fun insertLocation() = launch {
        database.pointMapDao().insertItem(routeUser)
    }

    override fun onLocationChanged(location: Location) {
        Log.v("infoService","Latitude: " + location.latitude + " , Longitude: " + location.longitude)
        val data = LocalDateTime.now()
        routeUser.add(PointMap(data.toString(), location.latitude.toString(), location.longitude.toString()))
    }
}

