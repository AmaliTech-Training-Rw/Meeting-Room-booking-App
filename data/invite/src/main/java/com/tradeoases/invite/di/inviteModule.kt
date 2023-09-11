package com.tradeoases.invite.di

import com.amalitech.core.data.BASE_URL
import com.tradeoases.invite.data_source.remote.InviteApiService
import com.tradeoases.invite.repository.InviteRepository
import com.tradeoases.invite.repository.InviteRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val inviteModule = module {
    single<InviteRepository> {
        InviteRepositoryImpl(get())
    }

    single<InviteApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
            .create()
    }
}