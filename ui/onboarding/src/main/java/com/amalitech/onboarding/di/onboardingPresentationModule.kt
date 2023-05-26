package com.amalitech.onboarding.di

import com.amalitech.onboarding.OnboardingViewModel
import com.amalitech.onboarding.login.LoginViewModel
import com.amalitech.onboarding.reset_password.ResetPasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingPresentationModule = module {
    viewModel {
        OnboardingViewModel(get())
    }
    single {
        LoginViewModel(get())
    }

    viewModel {
        ResetPasswordViewModel(get())
    }
}
