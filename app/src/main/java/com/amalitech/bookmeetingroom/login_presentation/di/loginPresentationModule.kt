package com.amalitech.bookmeetingroom.login_presentation.di

import com.amalitech.bookmeetingroom.login_presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginPresentationModule = module {
    viewModel {
        LoginViewModel(get())
    }
}
