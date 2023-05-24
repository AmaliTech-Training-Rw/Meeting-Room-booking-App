package com.example.authentication.di

import com.example.authentication.use_case.LogIn
import com.example.authentication.use_case.LoginUseCase
import com.example.authentication.use_case.ValidateEmail
import com.example.authentication.use_case.ValidatePassword
import org.koin.dsl.module

val domainAuthenticationModule = module {
    single {
        LogIn()
    }

    single {
        ValidateEmail()
    }

    single {
        ValidatePassword()
    }

    single {
        LoginUseCase(
            get(),
            get(),
            get()
        )
    }
}
