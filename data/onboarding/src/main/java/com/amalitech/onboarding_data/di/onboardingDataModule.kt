package com.amalitech.onboarding_data.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.amalitech.onboarding_domain.preferences.OnboardingSharedPreferences
import com.amalitech.onboarding_data.preferences.OnboardingSharedPreferencesImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val onboardingDataModule = module {

    single<SharedPreferences> {
        androidContext().getSharedPreferences("shared_pref", MODE_PRIVATE)
    }

    single<OnboardingSharedPreferences> {
        OnboardingSharedPreferencesImpl(get())
    }
}
