package com.example.locationinbackground.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PointMap::class], version = 1, exportSchema = false)
abstract class DataBase: RoomDatabase() {

    abstract fun pointMapDao(): PointMapDao

    companion object {
        fun getDatabase(context: Context): DataBase{
            return Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "item_database"
                ).build()
        }
    }
}