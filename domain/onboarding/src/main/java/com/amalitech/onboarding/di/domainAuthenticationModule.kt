package com.amalitech.onboarding.di

import com.amalitech.onboarding.login.use_case.ValidatePassword
import com.amalitech.onboarding.reset_password.CheckPasswordsMatch
import com.amalitech.onboarding.reset_password.ResetPassword
import com.amalitech.onboarding.reset_password.ResetPasswordUseCase
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

    single {
        CheckPasswordsMatch()
    }

    single {
        ResetPassword()
    }

    single {
        ResetPasswordUseCase(get(), get())
    }
}
