package com.ancoding.workmanager.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ancoding.workmanager.repository.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(var repository: LocationRepository) : ViewModel() {

    private val locationListLiveData =
        MutableLiveData<List<com.ancoding.workmanager.database.Location>>()

    fun getLocationLiveData():LiveData<List<com.ancoding.workmanager.database.Location>>{
        return  locationListLiveData
    }

    fun getAll() {
        CoroutineScope(Dispatchers.IO).launch {
            locationListLiveData.postValue(repository.getAll())
        }
    }
}