package com.amalitech.core.di

import android.content.Context
import android.content.SharedPreferences
import com.amalitech.core.data.BookMeetingRepositoryImpl
import com.amalitech.core.data.preferences.OnboardingSharedPreferencesImpl
import com.amalitech.core.domain.BookMeetingRepository
import com.amalitech.core.domain.ValidatePasswordUseCase
import com.amalitech.core.domain.preferences.OnboardingSharedPreferences
import com.amalitech.core.domain.use_case.CheckPasswordsMatchUseCase
import com.amalitech.core.domain.use_case.CheckValuesNotBlankUseCase
import com.amalitech.core.domain.use_case.ValidateEmailUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single<BookMeetingRepository> {
        BookMeetingRepositoryImpl(get())
    }

    single<BookMeetingRepository> {
        BookMeetingRepositoryImpl(get())
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
    }

    single<OnboardingSharedPreferences> {
        OnboardingSharedPreferencesImpl(get())
    }
    single<BookMeetingRepository> {
        BookMeetingRepositoryImpl(get())
    }

    single {
        ValidateEmailUseCase()
    }

    single {
        CheckPasswordsMatchUseCase()
    }

    single {
        CheckValuesNotBlankUseCase()
    }

    single {
        ValidatePasswordUseCase()
    }
}
