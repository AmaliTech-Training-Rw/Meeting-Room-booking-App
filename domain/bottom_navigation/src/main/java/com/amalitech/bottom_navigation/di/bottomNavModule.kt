package com.amalitech.bottom_navigation.di

import com.amalitech.bottom_navigation.use_case.GetInvitationsNumber
import org.koin.dsl.module

val domainBottomNavModule = module {

    single {
        GetInvitationsNumber()
    }
}
