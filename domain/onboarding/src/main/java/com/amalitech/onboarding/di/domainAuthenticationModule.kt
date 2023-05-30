package com.amalitech.onboarding.di

import com.amalitech.onboarding.login.use_case.IsUserAdmin
import com.amalitech.onboarding.login.use_case.LogIn
import com.amalitech.onboarding.login.use_case.LoginUseCase
import com.amalitech.onboarding.login.use_case.ValidateEmail
import com.amalitech.onboarding.login.use_case.ValidatePassword
import org.koin.dsl.module

val onboardingDomainModule = module {
    single {
        LogIn()
    }

    single {
        ValidateEmail()
    }

    single {
        ValidatePassword()
    }

    single {
        IsUserAdmin()
    }

    single {
        LoginUseCase(
            get(),
            get(),
            get(),
            get()
        )
    }
}
