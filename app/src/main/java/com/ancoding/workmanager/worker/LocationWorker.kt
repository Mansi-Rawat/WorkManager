package com.ancoding.workmanager.worker

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.*
import com.ancoding.workmanager.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LocationWorker constructor(var context: Context, params: WorkerParameters) :
    Worker(context, params),
    KoinComponent {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val repository: LocationRepository by inject()

    override fun doWork(): Result {
        Log.e("Location", "Hey i am Location worker Success")
        initLocationClient()
        getLastLocation()
        return Result.success()
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.addLocation(location)
                }
            }
        }
    }

    private fun initLocationClient() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    }
}
