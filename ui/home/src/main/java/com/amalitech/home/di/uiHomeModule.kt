package com.amalitech.home.di

import com.amalitech.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiHomeModule = module {
    viewModel {
        HomeViewModel(get(), get())
    }
}
