package com.amalitech.booking.di

import com.amalitech.booking.BookingViewModel
import com.amalitech.booking.history.BookingHistoryViewModel
import com.amalitech.booking.requests.BookingRequestViewModel
import com.amalitech.booking.requests.detail.BookingRequestDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationBookingModule = module {
    viewModel {
        BookingViewModel(get())
    }

    viewModel {
        BookingRequestViewModel(get())
    }

    viewModel {
        BookingRequestDetailViewModel(get())
    }

    viewModel {
        BookingHistoryViewModel(get())
    }
}
