package com.example.onboarding_presentation.di

import com.example.onboarding_presentation.OnboardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingPresentationModule = module {
    viewModel {
        OnboardingViewModel(get())
    }
}
