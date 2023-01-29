package com.ancoding.workmanager.di

import android.app.Application
import androidx.room.Room
import androidx.work.WorkManager
import com.ancoding.workmanager.database.LocationDao
import com.ancoding.workmanager.database.LocationDataBase
import com.ancoding.workmanager.repository.LocationRepository
import com.ancoding.workmanager.viewmodel.LocationViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LocationViewModel(get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): LocationDataBase {
        return Room.databaseBuilder(application, LocationDataBase::class.java, "location")
            .fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }


    fun provideDao(database: LocationDataBase): LocationDao {
        return database.locationDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideUserRepository(dao: LocationDao): LocationRepository {
        return LocationRepository(dao)
    }

    single { provideUserRepository(get()) }
}
val workManagerModule = module {
    fun provideWorkManagerRepository(application: Application): WorkManager {
        return WorkManager.getInstance(application)
    }

    single { provideWorkManagerRepository(androidApplication()) }
}