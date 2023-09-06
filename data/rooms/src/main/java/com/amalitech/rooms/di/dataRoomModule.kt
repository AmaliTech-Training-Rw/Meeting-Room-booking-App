package com.amalitech.rooms.di

import com.amalitech.core.data.BASE_URL
import com.amalitech.rooms.remote.RoomsApiService
import com.amalitech.rooms.repository.RoomRepository
import com.amalitech.rooms.repository.RoomRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


val dataRoomModule = module {
    single<RoomRepository> {
        RoomRepositoryImpl(get())
    }

    single<RoomsApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
            .create()
    }
}