package com.ancoding.workmanager.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import com.ancoding.workmanager.repository.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(var repository: LocationRepository) : ViewModel() {

    fun addLocation(location:Location) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addLocation(location)
        }
    }
}