package com.amalitech.bookmeetingroom

import android.app.Application
import com.amalitech.bookmeetingroom.login_domain.di.loginDomainModule
import com.amalitech.bookmeetingroom.login_presentation.di.loginPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp.applicationContext)

            modules(loginDomainModule, loginPresentationModule)
        }
    }
}