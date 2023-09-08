package com.amalitech.core.di

import android.content.Context
import android.content.SharedPreferences
import com.amalitech.core.data.BASE_URL
import com.amalitech.core.data.BookMeetingRepositoryImpl
import com.amalitech.core.data.data_source.remote.CoreApiService
import com.amalitech.core.data.preferences.OnboardingSharedPreferencesImpl
import com.amalitech.core.data.repository.CoreRepository
import com.amalitech.core.domain.BookMeetingRepository
import com.amalitech.core.domain.ValidatePasswordUseCase
import com.amalitech.core.domain.preferences.OnboardingSharedPreferences
import com.amalitech.core.domain.use_case.CheckPasswordsMatchUseCase
import com.amalitech.core.domain.use_case.CheckValuesNotBlankUseCase
import com.amalitech.core.domain.use_case.FetchLocationsUseCase
import com.amalitech.core.domain.use_case.ValidateEmailUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val coreModule = module {
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

    single {
        FetchLocationsUseCase(get())
    }

    single {
        CoreRepository(get())
    }

    single<CoreApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
            .create()
    }
}
