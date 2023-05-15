package com.amalitech.bookmeetingroom

import android.app.Application
import com.amalitech.bookmeetingroom.authentication_domain.di.authenticationDomainModule
import com.amalitech.bookmeetingroom.authentication_presentation.di.authenticationPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp.applicationContext)

            modules(authenticationDomainModule, authenticationPresentationModule)
        }
    }
}