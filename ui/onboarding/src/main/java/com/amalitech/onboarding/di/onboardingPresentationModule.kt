package com.amalitech.onboarding.di

import com.amalitech.onboarding.login.LoginViewModel
import com.amalitech.onboarding.reset_password.ResetPasswordViewModel
import com.amalitech.onboarding.splash_screen.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingPresentationModule = module {

    single {
        LoginViewModel(get(), get())
    }

    viewModel {
        ResetPasswordViewModel(get())
    }

    viewModel {
        SplashScreenViewModel(get())
    }

    viewModel {
        ResetPasswordViewModel(get())
    }
}
