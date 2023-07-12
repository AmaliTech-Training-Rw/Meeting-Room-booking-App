package com.amalitech.room.di

import com.amalitech.room.book_room.use_case.BookRoomUseCase
import com.amalitech.room.book_room.use_case.BookRoomUseCasesWrapper
import com.amalitech.room.book_room.use_case.GetRoomUseCase
import org.koin.dsl.module

val domainRoomModule = module {
    single {
        GetRoomUseCase()
    }

    single {
        BookRoomUseCase()
    }

    single {
        BookRoomUseCasesWrapper(get(), get(), get())
    }
}
