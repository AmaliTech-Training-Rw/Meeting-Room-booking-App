package com.amalitech.booking.di

import com.amalitech.booking.history.FetchBookingHistoryUseCase
import com.amalitech.booking.request.detail.GetBookingRequestDetailUseCase
import com.amalitech.booking.request.use_case.BookingRequestsUseCaseWrapper
import com.amalitech.booking.request.use_case.FetchBookingsUseCase
import com.amalitech.booking.request.use_case.UpdateBookingStatusUseCase
import com.amalitech.booking.use_case.BookingUseCase
import com.amalitech.booking.use_case.GetBookingsUseCase
import org.koin.dsl.module

val domainBookingModule = module {
    single {
        BookingUseCase(get())
    }

    single {
        GetBookingsUseCase(get())
    }

    single {
        FetchBookingsUseCase(get())
    }

    single {
        UpdateBookingStatusUseCase(get())
    }

    single {
        BookingRequestsUseCaseWrapper(get(), get())
    }
    single {
        GetBookingRequestDetailUseCase(get())
    }

    single {
        FetchBookingHistoryUseCase(get())
    }
}
