package com.example.room.di

import com.example.room.book_room.use_case.BookRoom
import com.example.room.book_room.use_case.BookRoomUseCase
import com.example.room.book_room.use_case.GetBookableRoomUseCase
import org.koin.dsl.module

val domainRoomModule = module {
    single {
        GetBookableRoomUseCase()
    }

    single {
        BookRoom()
    }

    single {
        BookRoomUseCase(get(), get(), get())
    }
}
