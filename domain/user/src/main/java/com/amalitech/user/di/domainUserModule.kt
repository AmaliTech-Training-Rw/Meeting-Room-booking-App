package com.amalitech.user.di

import com.amalitech.user.profile.use_case.GetUserUseCase
import com.amalitech.user.profile.use_case.ProfileUseCaseWrapper
import com.amalitech.user.profile.use_case.SaveUserUseCase
import org.koin.dsl.module

val domainUserModule = module {
    single {
        GetUserUseCase(get())
    }

    single {
        SaveUserUseCase(get())
    }

    single {
        ProfileUseCaseWrapper(get(), get())
    }
}