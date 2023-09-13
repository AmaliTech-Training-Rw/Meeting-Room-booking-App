package com.amalitech.rooms.di

import com.amalitech.rooms.AddRoomViewModel
import com.amalitech.rooms.RoomViewModel
import com.amalitech.rooms.book_room.BookRoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val roomPresentationModule = module {
    viewModel {
        RoomViewModel(get())
    }

    viewModel {
        BookRoomViewModel(get())
    }
    viewModel {
        AddRoomViewModel(get(), get(), get())
    }
}
