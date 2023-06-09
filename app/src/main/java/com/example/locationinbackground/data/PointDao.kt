package com.example.locationinbackground.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PointMapDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: List<PointMap>)

    @Update
    suspend fun updateItem(item: PointMap)

    @Delete
    suspend fun deleteItem(item: PointMap)

    @Query("SELECT * FROM PointMap")
    fun getItem(): Flow<List<PointMap>>
}