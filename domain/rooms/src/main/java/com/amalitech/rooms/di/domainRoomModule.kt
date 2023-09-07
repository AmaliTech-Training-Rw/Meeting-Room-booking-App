package com.amalitech.rooms.di

import com.amalitech.rooms.book_room.use_case.BookRoomUseCase
import com.amalitech.rooms.book_room.use_case.BookRoomUseCasesWrapper
import com.amalitech.rooms.book_room.use_case.GetRoomUseCase
import com.amalitech.rooms.usecase.AddRoomUseCase
import com.amalitech.rooms.usecase.DeleteRoomUseCase
import com.amalitech.rooms.usecase.FetchRoomsUseCase
import com.amalitech.rooms.usecase.RoomUseCaseWrapper
import org.koin.dsl.module


val domainRoomsModule = module {
    single {
        FetchRoomsUseCase(get())
    }

    single {
        DeleteRoomUseCase(get())
    }

    single {
        RoomUseCaseWrapper(get(), get())
    }

    single {
        GetRoomUseCase()
    }

    single {
        BookRoomUseCase()
    }

    single {
        BookRoomUseCasesWrapper(get(), get(), get())
    }

    single {
        AddRoomUseCase(get())
    }
}
