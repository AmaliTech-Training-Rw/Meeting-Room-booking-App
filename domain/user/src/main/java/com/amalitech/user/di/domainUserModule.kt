package com.amalitech.user.di

import com.amalitech.user.profile.use_case.GetUserUseCase
import com.amalitech.user.profile.use_case.ProfileUseCaseWrapper
import org.koin.dsl.module

val domainUserModule = module {
    single {
        GetUserUseCase(get())
    }

    single {
        ProfileUseCaseWrapper(get())
    }
}
