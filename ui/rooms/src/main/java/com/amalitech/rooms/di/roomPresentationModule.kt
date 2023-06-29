package com.amalitech.rooms.di

import com.amalitech.rooms.RoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val roomPresentationModule = module {
    viewModel {
        RoomViewModel(get())
    }
}