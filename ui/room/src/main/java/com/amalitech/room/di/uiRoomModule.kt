package com.amalitech.room.di

import com.amalitech.rooms.book_room.BookRoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiRoomModule = module {
    viewModel {
        com.amalitech.rooms.book_room.BookRoomViewModel(get())
    }
}
