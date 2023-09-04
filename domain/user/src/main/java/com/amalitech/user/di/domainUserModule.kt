package com.amalitech.user.di

import com.amalitech.user.profile.use_case.GetUserUseCase
import com.amalitech.user.profile.use_case.LogoutUseCase
import com.amalitech.user.profile.use_case.ProfileUseCaseWrapper
import com.amalitech.user.profile.use_case.SaveUserUseCase
import com.amalitech.user.profile.use_case.UpdateProfileUseCase
import org.koin.dsl.module

val domainUserModule = module {
    single {
        GetUserUseCase(get())
    }

    single {
        SaveUserUseCase(get())
    }

    single {
        UpdateProfileUseCase(get())
    }

    single {
        LogoutUseCase(get())
    }

    single {
        ProfileUseCaseWrapper(get(), get(), get(), get(), get(), get(), get())
    }
}
