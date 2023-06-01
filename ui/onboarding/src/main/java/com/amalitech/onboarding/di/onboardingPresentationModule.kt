package com.amalitech.onboarding.di

import com.amalitech.onboarding.forgot_password.ForgotPasswordViewModel
import com.amalitech.onboarding.login.LoginViewModel
import com.amalitech.onboarding.reset_password.ResetPasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingPresentationModule = module {
    single {
        LoginViewModel(get())
    }

    single {
        ForgotPasswordViewModel(get())
    }

    viewModel {
        ResetPasswordViewModel(get())
    }
}
