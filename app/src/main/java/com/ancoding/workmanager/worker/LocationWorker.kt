package com.ancoding.workmanager.worker

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.*
import com.ancoding.workmanager.viewmodel.LocationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationWorker(var context: Context, params: WorkerParameters) : Worker(context, params) {
      private lateinit var fusedLocationProviderClient: FusedLocationProviderClient



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
                viewModel.addLocation(location)
            }
        }
    }

    private fun initLocationClient() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    }
}
