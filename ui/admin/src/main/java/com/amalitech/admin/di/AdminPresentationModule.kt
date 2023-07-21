package com.amalitech.admin.di

import com.amalitech.admin.room.AddRoomViewModel
import com.amalitech.admin.room.usecase.AddRoom
import com.amalitech.admin.room.usecase.GetLocation
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val adminPresentationModule = module {
    viewModel {
        AddRoomViewModel(get(), get())
    }

    single {
        GetLocation()
    }

    single {
        AddRoom()
    }
}
