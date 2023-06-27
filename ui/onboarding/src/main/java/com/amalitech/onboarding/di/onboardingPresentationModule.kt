package com.amalitech.onboarding.di

import com.amalitech.onboarding.forgot_password.ForgotPasswordViewModel
import com.amalitech.onboarding.login.LoginViewModel
import com.amalitech.onboarding.reset_password.ResetPasswordViewModel
import com.amalitech.onboarding.signup.SignupViewModel
import com.amalitech.onboarding.splash_screen.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingPresentationModule = module {

    viewModel {
        LoginViewModel(get(), get())
    }

    viewModel {
        ResetPasswordViewModel(get())
    }

    viewModel {
        SplashScreenViewModel(get())
    }

    viewModel {
        ForgotPasswordViewModel(get())
    }

    viewModel {
        SignupViewModel(get())
    }
}
