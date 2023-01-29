package com.ancoding.workmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Location::class], version = 1)
abstract class LocationDataBase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}