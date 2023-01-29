package com.ancoding.workmanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LocationDao {
    @Insert
    suspend fun addLocation(location :Location)

    @Query("SELECT * FROM location")
    suspend fun getAll(): List<Location>
}