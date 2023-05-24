package com.example.authentication.di

import com.example.authentication.login.LoginViewModel
import com.example.authentication.use_case.LoginUseCase
import org.koin.dsl.module

val uiAuthenticationModule = module {
    single {
        LoginViewModel(get())
    }
}
