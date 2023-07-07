package com.amalitech.home.di

import com.amalitech.home.book_room.use_case.BookRoom
import com.amalitech.home.book_room.use_case.BookRoomUseCase
import com.amalitech.home.book_room.use_case.GetBookableRoom
import com.amalitech.home.book_room.use_case.ValidateEmail
import com.amalitech.home.use_case.FetchBookings
import com.amalitech.home.use_case.HomeUseCase
import org.koin.dsl.module

val domainHomeModule = module {
    single {
        FetchBookings()
    }

    single {
        HomeUseCase(get())
    }

    single {
        ValidateEmail()
    }

    single {
        GetBookableRoom()
    }

    single {
        BookRoom()
    }

    single {
        BookRoomUseCase(get(), get(), get())
    }
}
