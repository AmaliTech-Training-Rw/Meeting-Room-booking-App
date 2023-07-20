package com.amalitech.home.di

import com.amalitech.home.HomeViewModel
import com.amalitech.home.room.add.AddRoomViewModel
import com.amalitech.home.use_case.GetLocation
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiHomeModule = module {
    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        AddRoomViewModel(get())
    }

    single {
        GetLocation()
    }
}
