package com.example.locationinbackground.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.os.IBinder
import android.os.Handler
import android.util.Log
import android.location.LocationManager
import com.example.locationinbackground.DataApplication
import com.example.locationinbackground.data.DataBase
import com.example.locationinbackground.data.PointMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LocationService() : Service(), LocationListener, CoroutineScope{
    private val job: Job = Job()
    private lateinit var database: DataBase
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private lateinit var locationManager: LocationManager

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // Send a notification that service is started

        database = DataBase.getDatabase(this)
        clearDB()
        Log.v("infoService","Service started.")
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Do a periodic task
        mHandler = Handler()
        mRunnable = Runnable { showLocater() }
        mHandler.postDelayed(mRunnable, 3000)

        return START_STICKY
    }

    private fun clearDB() = launch{
        database.clearAllTables()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("infoService","Service destroyed.")
        mHandler.removeCallbacks(mRunnable)
    }

    // Custom method to do a task
    private fun showLocater() {
        getLocate()
        mHandler.postDelayed(mRunnable, 3000)
    }

    @SuppressLint("MissingPermission")
    private fun getLocate(){
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5f, this)
    }

    private fun insertLocation(item: PointMap) = launch {
        database.pointMapDao().insertItem(item)
    }

    override fun onLocationChanged(location: Location) {
        Log.v("infoService","Latitude: " + location.latitude + " , Longitude: " + location.longitude)
        insertLocation(PointMap(null, location.latitude.toString(), location.longitude.toString()))
    }
}

