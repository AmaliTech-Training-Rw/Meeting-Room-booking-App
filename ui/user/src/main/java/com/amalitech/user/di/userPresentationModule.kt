package com.amalitech.user.di

import com.amalitech.user.UserViewModel
import com.amalitech.user.usecases.GetUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userPresentationModule = module {
    viewModel {
        UserViewModel(get())
    }

    single {
        GetUseCase(get())
    }
}