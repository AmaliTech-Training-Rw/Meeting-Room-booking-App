package com.amalitech.bookmeetingroom

import android.app.Application
import com.amalitech.core.di.appModule
import com.amalitech.core.di.dataModule
import com.amalitech.core.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BookMeetingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@BookMeetingApplication)
            modules(listOf(appModule, networkModule,
                dataModule
            ))
        }
    }
}