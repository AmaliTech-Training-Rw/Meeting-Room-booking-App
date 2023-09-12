package com.amalitech.home.di

import com.amalitech.core.data.BASE_URL
import com.amalitech.home.data_source.remote.HomeApiService
import com.amalitech.home.repository.HomeRepository
import com.amalitech.home.repository.HomeRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val dataHomeModule = module {
    single<HomeRepository> {
        HomeRepositoryImpl(get())
    }

    single<HomeApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
            .create()
    }
}
