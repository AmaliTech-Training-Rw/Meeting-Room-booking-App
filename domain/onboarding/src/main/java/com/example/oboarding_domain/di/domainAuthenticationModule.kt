package com.example.oboarding_domain.di

import com.example.oboarding_domain.login.use_case.LogIn
import com.example.oboarding_domain.login.use_case.LoginUseCase
import com.example.oboarding_domain.login.use_case.ValidateEmail
import com.example.oboarding_domain.login.use_case.ValidatePassword
import org.koin.dsl.module

val domainOnBoardingModule = module {
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
