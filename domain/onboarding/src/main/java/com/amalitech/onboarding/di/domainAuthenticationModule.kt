package com.amalitech.onboarding.di

import com.amalitech.onboarding.forgot_password.use_case.SendResetLink
import com.amalitech.onboarding.login.use_case.LogIn
import com.amalitech.onboarding.login.use_case.LoginUseCase
import com.amalitech.onboarding.login.use_case.ValidateEmail
import com.amalitech.onboarding.login.use_case.ValidatePassword
import com.amalitech.onboarding.reset_password.CheckPasswordsMatch
import com.amalitech.onboarding.reset_password.ResetPassword
import com.amalitech.onboarding.reset_password.ResetPasswordUseCase
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
        SendResetLink()
    }

    single {
        LoginUseCase(
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
