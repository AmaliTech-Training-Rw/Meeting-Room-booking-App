package com.amalitech.user.di

import com.amalitech.user.UserViewModel
import com.amalitech.user.domain.usecases.GetUsers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userPresentationModule = module {
    viewModel {
        UserViewModel(get())
    }

    single {
        GetUsers(get())
    }
}