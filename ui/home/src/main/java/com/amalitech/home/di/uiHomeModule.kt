package com.amalitech.home.di

import com.amalitech.home.HomeViewModel
import com.amalitech.home.book_room.BookRoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiHomeModule = module {
    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        BookRoomViewModel(get())
    }
}
