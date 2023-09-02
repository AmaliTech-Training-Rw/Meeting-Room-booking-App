package com.amalitech.user.di

import com.amalitech.user.UserViewModel
import com.amalitech.user.adduser.AddUserViewModel
import com.amalitech.user.usecases.FetchRemoteUsersCase
import com.amalitech.user.usecases.GetUseCase
import com.amalitech.user.usecases.InviteUserUseCase
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
        GetUseCase(get())
    }

    single {
        FetchRemoteUsersCase(get())
    }

    single {
        InviteUserUseCase(get())
    }
}