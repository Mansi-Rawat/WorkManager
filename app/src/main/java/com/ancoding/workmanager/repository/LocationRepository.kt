package com.ancoding.workmanager.repository

import com.ancoding.workmanager.database.Location
import com.ancoding.workmanager.database.LocationDao

class LocationRepository(var locationDao: LocationDao) {
    suspend fun addLocation(location:android.location.Location){
        locationDao.addLocation(
            Location(
                latitude = location.latitude,
                longitude = location.longitude
            )
        )
    }
    suspend fun getAll(): List<Location> {
        return locationDao.getAll()
    }
}