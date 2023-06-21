package com.amalitech.rooms.di

import com.amalitech.rooms.repository.RoomRepository
import com.amalitech.rooms.repository.RoomRepositoryImpl
import org.koin.dsl.module


val dataRoomModule = module {
    single<RoomRepository> {
        RoomRepositoryImpl()
    }
}