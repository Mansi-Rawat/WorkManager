package com.ancoding.workmanager.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ancoding.workmanager.databinding.ActivityMainBinding
import com.ancoding.workmanager.viewmodel.LocationViewModel
import com.ancoding.workmanager.worker.LocationWorker
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<LocationViewModel>()

    val workManager: WorkManager by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addOnClickListeners()

        viewModel.getLocationLiveData().observe(this) { locations ->
            locations.forEach {
                binding.tvTotal.text =""+binding.tvTotal.text +"\n"+it.latitude+"-"+it.longitude
            }

        }
    }

    private fun addOnClickListeners() {
        binding.btStart.setOnClickListener {
            if (isLocationPermissionGranted()) startTrackingLocation()
        }
        binding.btFetchAll.setOnClickListener {
            viewModel.getAll()
        }
    }

    private fun startTrackingLocation() {
        val locationWorker =
            PeriodicWorkRequestBuilder<LocationWorker>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "LocationTag", ExistingPeriodicWorkPolicy.KEEP, locationWorker
        )
    }

    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            false
        } else {
            true
        }
    }

    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    startTrackingLocation()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Log.e("Permission", "course")

                }
                else -> {
                    Log.e("Permission", "Not Granted")
                }
            }
        }
}



