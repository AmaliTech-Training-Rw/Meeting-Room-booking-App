package com.amalitech.bookmeetingroom

import android.app.Application
import com.amalitech.admin.di.adminPresentationModule
import com.amalitech.booking.di.domainBookingModule
import com.amalitech.booking.di.presentationBookingModule
import com.amalitech.bookmeetingroom.di.appModule
import com.amalitech.core.di.coreModule
import com.amalitech.core.di.networkModule
import com.amalitech.home.di.domainHomeModule
import com.amalitech.home.di.uiHomeModule
import com.amalitech.onboarding.di.onboardingDomainModule
import com.amalitech.onboarding.di.onboardingPresentationModule
import com.amalitech.onboarding_data.di.onboardingDataModule
import com.amalitech.rooms.di.dataRoomModule
import com.amalitech.rooms.di.domainRoomModule
import com.amalitech.rooms.di.roomPresentationModule
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
                onboardingDomainModule,
                appModule,
                roomPresentationModule,
                dataRoomModule,
                domainRoomModule,
                adminPresentationModule,
                domainHomeModule,
                uiHomeModule,
                domainBookingModule,
                presentationBookingModule
            )
        }
    }
}
