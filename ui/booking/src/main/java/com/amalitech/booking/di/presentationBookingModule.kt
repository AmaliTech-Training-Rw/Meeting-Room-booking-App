package com.amalitech.booking.di

import com.amalitech.booking.BookingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationBookingModule = module {
    viewModel {
        BookingViewModel(get())
    }
}
