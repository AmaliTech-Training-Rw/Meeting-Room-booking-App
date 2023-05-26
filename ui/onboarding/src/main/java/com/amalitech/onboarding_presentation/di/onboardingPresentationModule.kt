package com.amalitech.onboarding_presentation.di

import com.amalitech.onboarding_presentation.OnboardingViewModel
import com.amalitech.onboarding_presentation.login.LoginViewModel
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
