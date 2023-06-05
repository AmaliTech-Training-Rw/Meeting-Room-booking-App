package com.amalitech.rooms.di

import com.amalitech.rooms.usecase.GetRoomsUseCase
import org.koin.dsl.module


val domainRoomModule = module {
    single {
        GetRoomsUseCase(get())
    }
}