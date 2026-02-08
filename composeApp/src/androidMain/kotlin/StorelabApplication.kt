package com.pradeep.storelabassignment

import android.app.Application
import com.pradeep.storelabassignment.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

/**
 * Android application class for the StorelabAssignment app.
 * It initialises the Koin dependency injection framework for android.
 */
class StorelabApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@StorelabApplication)
        }
    }
}