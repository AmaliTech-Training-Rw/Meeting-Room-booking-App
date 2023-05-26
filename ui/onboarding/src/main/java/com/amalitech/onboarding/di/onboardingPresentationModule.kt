package com.amalitech.onboarding.di

import com.amalitech.onboarding.OnboardingViewModel
import com.amalitech.onboarding.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingPresentationModule = module {
    viewModel {
        OnboardingViewModel(get())
    }
    single {
        LoginViewModel(get())
    }
}
