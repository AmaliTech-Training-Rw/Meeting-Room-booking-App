package com.amalitech.dashboard.di

import com.amalitech.dashboard.use_case.FetchDashboardDataUseCase
import org.koin.dsl.module

val domainDashboardModule = module {
    single {
        FetchDashboardDataUseCase(get())
    }

    single {

    }
}
