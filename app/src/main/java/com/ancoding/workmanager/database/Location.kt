package com.ancoding.workmanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(@PrimaryKey(autoGenerate = true) var id:Int?=null,
                    @ColumnInfo(name = "latitude") val latitude: Double?,
                    @ColumnInfo(name = "longitude") val longitude: Double?) {


}