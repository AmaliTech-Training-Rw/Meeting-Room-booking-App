package com.amalitech.home.di

import com.amalitech.home.use_case.FetchBookings
import com.amalitech.home.use_case.HomeUseCase
import org.koin.dsl.module

val domainHomeModule = module {
    single {
        FetchBookings()
    }

    single {
        HomeUseCase(get())
    }
}
