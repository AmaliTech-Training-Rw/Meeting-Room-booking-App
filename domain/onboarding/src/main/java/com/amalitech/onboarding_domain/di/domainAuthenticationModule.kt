package com.amalitech.onboarding_domain.di

import com.amalitech.onboarding_domain.login.use_case.ValidatePassword
import org.koin.dsl.module

val onboardingDomainModule = module {
    single {
        com.amalitech.onboarding_domain.login.use_case.LogIn()
    }

    single {
        com.amalitech.onboarding_domain.login.use_case.ValidateEmail()
    }

    single {
        ValidatePassword()
    }

    single {
        com.amalitech.onboarding_domain.login.use_case.LoginUseCase(
            get(),
            get(),
            get()
        )
    }
}
