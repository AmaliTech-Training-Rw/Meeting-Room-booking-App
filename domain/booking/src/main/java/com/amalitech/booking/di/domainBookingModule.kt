package com.amalitech.booking.di

import com.amalitech.booking.use_case.BookingUseCase
import com.amalitech.booking.use_case.GetBookingsUseCase
import org.koin.dsl.module

val domainBookingModule = module {
    single {
        BookingUseCase(get())
    }

    single {
        GetBookingsUseCase()
    }
}
