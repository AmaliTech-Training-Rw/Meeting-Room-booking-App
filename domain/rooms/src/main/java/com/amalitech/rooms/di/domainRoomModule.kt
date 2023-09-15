package com.amalitech.rooms.di

import com.amalitech.rooms.book_room.use_case.BookRoomUseCase
import com.amalitech.rooms.book_room.use_case.BookRoomUseCasesWrapper
import com.amalitech.rooms.book_room.use_case.GetEndTimeUseCase
import com.amalitech.rooms.book_room.use_case.GetStartTimeUseCase
import com.amalitech.rooms.usecase.AddRoomUseCase
import com.amalitech.rooms.usecase.DeleteRoomUseCase
import com.amalitech.rooms.usecase.FetchRoomsUseCase
import com.amalitech.rooms.usecase.FindRoomUseCase
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
        BookRoomUseCase(get())
    }

    single {
        BookRoomUseCasesWrapper(get(), get(), get(), get(), get())
    }

    single {
        AddRoomUseCase(get())
    }

    single {
        FindRoomUseCase(get())
    }

    single {
        GetStartTimeUseCase(get())
    }

    single {
        GetEndTimeUseCase(get())
    }
}
