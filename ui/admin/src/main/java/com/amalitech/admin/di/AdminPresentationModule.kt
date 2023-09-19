package com.amalitech.admin.di

import com.amalitech.admin.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val adminPresentationModule = module {
    viewModel {
        DashboardViewModel(get())
    }
}
