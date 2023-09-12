package com.amalitech.home.di

import com.amalitech.core.domain.use_case.ValidateEmailUseCase
import com.amalitech.home.use_case.FetchBookingsUseCase
import com.amalitech.home.use_case.FetchRoomsUseCase
import com.amalitech.home.use_case.HomeUseCaseWrapper
import org.koin.dsl.module

val domainHomeModule = module {
    single {
        FetchBookingsUseCase(get())
    }

    single {
        HomeUseCaseWrapper(get())
    }

    single {
        ValidateEmailUseCase()
    }

    single {
        FetchRoomsUseCase(get())
    }
}
