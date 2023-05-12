package com.amalitech.bookmeetingroom.login_domain.di

import com.amalitech.bookmeetingroom.login_domain.use_case.LogIn
import com.amalitech.bookmeetingroom.login_domain.use_case.UseCase
import com.amalitech.bookmeetingroom.login_domain.use_case.ValidateEmail
import com.amalitech.bookmeetingroom.login_domain.use_case.ValidatePassword
import org.koin.dsl.module

val loginDomainModule = module {
    single {
        ValidateEmail()
    }
    single {
        LogIn()
    }
    single {
        ValidatePassword()
    }
    single {
        UseCase(
            get(),
            get(),
            get()
        )
    }
}
