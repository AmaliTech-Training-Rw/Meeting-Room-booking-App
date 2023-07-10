package com.example.room.di

import com.example.room.book_room.BookRoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiRoomModule = module {
    viewModel {
        BookRoomViewModel(get())
    }
}
