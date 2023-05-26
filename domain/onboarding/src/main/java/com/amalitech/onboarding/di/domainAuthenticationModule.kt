package com.amalitech.onboarding.di

import com.amalitech.onboarding.login.use_case.ValidatePassword
import org.koin.dsl.module

val onboardingDomainModule = module {
    single {
        com.amalitech.onboarding.login.use_case.LogIn()
    }

    single {
        com.amalitech.onboarding.login.use_case.ValidateEmail()
    }

    single {
        ValidatePassword()
    }

    single {
        com.amalitech.onboarding.login.use_case.LoginUseCase(
            get(),
            get(),
            get()
        )
    }
}
