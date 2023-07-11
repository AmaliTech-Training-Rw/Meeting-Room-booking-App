package com.amalitech.onboarding.di

import com.amalitech.onboarding.forgot_password.use_case.ForgotPasswordUseCase
import com.amalitech.onboarding.forgot_password.use_case.SendResetLink
import com.amalitech.onboarding.login.use_case.IsUserAdmin
import com.amalitech.onboarding.login.use_case.LogIn
import com.amalitech.onboarding.login.use_case.LoginUseCase
import com.amalitech.core.domain.use_case.ValidateEmail
import com.amalitech.onboarding.login.use_case.ValidatePassword
import com.amalitech.onboarding.reset_password.CheckPasswordsMatch
import com.amalitech.onboarding.reset_password.ResetPassword
import com.amalitech.onboarding.reset_password.ResetPasswordUseCase
import com.amalitech.onboarding.signup.use_case.CheckValuesNotBlank
import com.amalitech.onboarding.signup.use_case.FetchOrganizationsType
import com.amalitech.onboarding.signup.use_case.IsEmailAvailable
import com.amalitech.onboarding.signup.use_case.IsUsernameAvailable
import com.amalitech.onboarding.signup.use_case.Signup
import com.amalitech.onboarding.signup.use_case.SignupUseCase
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
        ResetPasswordUseCase(get(), get(), get())
    }

    single {
        ForgotPasswordUseCase(
            get(),
            get()
        )
    }

    single {
        IsUserAdmin()
    }

    single {
        IsEmailAvailable()
    }

    single {
        IsUsernameAvailable()
    }

    single {
        Signup()
    }

    single {
        FetchOrganizationsType()
    }

    single {
        CheckValuesNotBlank()
    }

    single {
        SignupUseCase(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}
