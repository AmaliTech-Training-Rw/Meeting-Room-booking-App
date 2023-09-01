package com.amalitech.user.di

import com.amalitech.user.UserViewModel
import com.amalitech.user.adduser.AddUserViewModel
import com.amalitech.user.usecases.AddUserUseCase
import com.amalitech.user.usecases.GetUserCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userPresentationModule = module {
    viewModel {
        UserViewModel(get(), get())
    }

    viewModel {
        AddUserViewModel(get())
    }

    single {
        GetUserCase(get())
    }

    single {
        AddUserUseCase(get())
    }
}