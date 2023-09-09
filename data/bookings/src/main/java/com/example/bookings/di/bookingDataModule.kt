package com.example.bookings.di

import com.amalitech.booking.repository.BookingRepository
import com.amalitech.core.data.BASE_URL
import com.example.bookings.data_source.remote.BookingApiService
import com.example.bookings.repository.BookingRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val bookingDataModule = module {
    single<BookingRepository> {
        BookingRepositoryImpl(get())
    }

    single<BookingApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
            .create()
    }
}
