package com.amalitech.core_ui.di

import com.amalitech.core_ui.CoreViewModel
import org.koin.dsl.module

val coreUiModule = module {
    single {
        CoreViewModel(get(), get())
    }
}
