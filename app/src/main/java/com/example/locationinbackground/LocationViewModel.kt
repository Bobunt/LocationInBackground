package com.example.locationinbackground

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.locationinbackground.data.PointMap
import com.example.locationinbackground.data.PointMapDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class LocationViewModel(private val pointMapDao: PointMapDao): ViewModel(), CoroutineScope {
    val allPoints: LiveData<List<PointMap>> = pointMapDao.getItem().asLiveData()
    private val job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job
}
class LocationViewModelFactory(private val pointMapDao: PointMapDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocationViewModel(pointMapDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}