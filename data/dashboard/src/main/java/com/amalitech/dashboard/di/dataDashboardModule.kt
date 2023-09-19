package com.amalitech.dashboard.di

import com.amalitech.core.data.BASE_URL
import com.amalitech.dashboard.data_source.remote.DashboardApiService
import com.amalitech.dashboard.repository.DashboardRepository
import com.amalitech.dashboard.repository.DashboardRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val dataDashboardModule = module {
    single <DashboardRepository> {
        DashboardRepositoryImpl(get())
    }

    single<DashboardApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
            .create()
    }
}
