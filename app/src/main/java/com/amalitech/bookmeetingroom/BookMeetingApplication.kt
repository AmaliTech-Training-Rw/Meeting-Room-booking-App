package com.amalitech.bookmeetingroom

import android.app.Application
import com.amalitech.core.di.coreModule
import com.amalitech.core.di.networkModule
import com.example.authentication.di.domainAuthenticationModule
import com.example.authentication.di.uiAuthenticationModule
import com.example.onboarding_data.di.onboardingDataModule
import com.example.onboarding_presentation.di.onboardingPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BookMeetingApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BookMeetingApplication)
            modules(coreModule, networkModule, onboardingDataModule, onboardingPresentationModule, uiAuthenticationModule, domainAuthenticationModule)
        }
    }
}