package com.amalitech.onboarding_data.di

import com.amalitech.core.data.BASE_URL
import com.amalitech.onboarding.repository.OnboardingRepository
import com.amalitech.onboarding_data.remote.OnboardingApiService
import com.amalitech.onboarding_data.repository.OnboardingRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val onboardingDataModule = module {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    single<OnboardingApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory
                    .create(moshi)
            )
            .build()
            .create()
    }

    single<OnboardingRepository> {
        OnboardingRepositoryImpl(get())
    }
}
