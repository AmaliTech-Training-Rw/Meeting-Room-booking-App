package com.amalitech.bookmeetingroom

import android.app.Application
import com.amalitech.core.di.coreModule
import com.amalitech.core.di.networkModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BookMeetingApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BookMeetingApplication)
            modules(coreModule, networkModule)
        }
    }
}