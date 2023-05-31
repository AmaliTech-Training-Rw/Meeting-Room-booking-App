package com.amalitech.bookmeetingroom

import android.app.Application
import com.amalitech.core.di.coreModule
import com.amalitech.core.di.networkModule
import com.amalitech.onboarding.di.onboardingDomainModule
import com.amalitech.onboarding.di.onboardingPresentationModule
import com.amalitech.onboarding_data.di.onboardingDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BookMeetingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BookMeetingApplication)
            modules(
                coreModule,
                networkModule,
                onboardingDataModule,
                onboardingPresentationModule,
                onboardingDomainModule
            )
        }
    }
}
