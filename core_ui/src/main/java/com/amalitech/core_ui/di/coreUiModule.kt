package com.amalitech.core_ui.di

import com.amalitech.core_ui.CoreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreUiModule = module {
    viewModel {
        CoreViewModel(get())
    }
}
