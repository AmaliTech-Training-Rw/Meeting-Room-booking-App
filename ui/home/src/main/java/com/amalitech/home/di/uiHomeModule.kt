package com.amalitech.home.di

import com.amalitech.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiHomeModule = module {
    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        com.amalitech.home.room.AddRoomViewModel(get())
    }

    single {
        com.amalitech.home.room.usecase.GetLocation()
    }
}
