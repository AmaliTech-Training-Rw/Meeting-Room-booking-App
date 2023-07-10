package com.amalitech.room.di

import com.amalitech.room.book_room.BookRoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiRoomModule = module {
    viewModel {
        BookRoomViewModel(get())
    }
}
