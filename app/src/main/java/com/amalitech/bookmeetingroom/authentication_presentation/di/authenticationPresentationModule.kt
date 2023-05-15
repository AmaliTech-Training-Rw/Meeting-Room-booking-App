package com.amalitech.bookmeetingroom.authentication_presentation.di

import com.amalitech.bookmeetingroom.authentication_presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authenticationPresentationModule = module {
    viewModel {
        LoginViewModel(get())
    }
}
