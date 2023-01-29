package com.ancoding.workmanager

import android.app.Application
import com.ancoding.workmanager.di.databaseModule
import com.ancoding.workmanager.di.repositoryModule
import com.ancoding.workmanager.di.viewModelModule
import com.ancoding.workmanager.di.workManagerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
             androidContext(this@BaseApplication)
            modules(listOf(repositoryModule, viewModelModule, databaseModule,workManagerModule))
        }
    }
}