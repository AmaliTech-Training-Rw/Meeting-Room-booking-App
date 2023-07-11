package com.amalitech.room.di

import com.amalitech.room.book_room.use_case.BookRoom
import com.amalitech.room.book_room.use_case.BookRoomUseCase
import com.amalitech.room.book_room.use_case.GetBookableRoomUseCase
import org.koin.dsl.module

val domainRoomModule = module {
    single {
        com.amalitech.room.book_room.use_case.GetBookableRoomUseCase()
    }

    single {
        com.amalitech.room.book_room.use_case.BookRoom()
    }

    single {
        com.amalitech.room.book_room.use_case.BookRoomUseCase(get(), get(), get())
    }
}
