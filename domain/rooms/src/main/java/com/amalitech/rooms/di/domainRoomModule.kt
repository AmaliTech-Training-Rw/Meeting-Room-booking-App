package com.amalitech.rooms.di

import com.amalitech.rooms.usecase.DeleteRoomUseCase
import com.amalitech.rooms.usecase.FetchRoomsUseCase
import com.amalitech.rooms.usecase.RoomUseCaseWrapper
import org.koin.dsl.module


val domainRoomModule = module {
    single {
        FetchRoomsUseCase(get())
    }

    single {
        DeleteRoomUseCase(get())
    }

    single {
        RoomUseCaseWrapper(get(), get())
    }
}