package com.amalitech.bookmeetingroom.authentication_domain.di

import com.amalitech.bookmeetingroom.authentication_domain.use_case.LogIn
import com.amalitech.bookmeetingroom.authentication_domain.use_case.AuthenticationUseCase
import com.amalitech.bookmeetingroom.authentication_domain.use_case.CheckPasswordsMatch
import com.amalitech.bookmeetingroom.authentication_domain.use_case.ResetPassword
import com.amalitech.bookmeetingroom.authentication_domain.use_case.SendResetLink
import com.amalitech.bookmeetingroom.authentication_domain.use_case.ValidateEmail
import com.amalitech.bookmeetingroom.authentication_domain.use_case.ValidatePassword
import org.koin.dsl.module

val authenticationDomainModule = module {
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
        CheckPasswordsMatch()
    }
    single {
        ResetPassword()
    }
    single {
        SendResetLink()
    }
    single {
        AuthenticationUseCase(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}
