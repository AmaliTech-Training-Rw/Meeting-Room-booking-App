package com.amalitech.bookmeetingroom.authentication_presentation.di

import com.amalitech.bookmeetingroom.authentication_presentation.forgot_password.ForgotPasswordViewModel
import com.amalitech.bookmeetingroom.authentication_presentation.login.LoginViewModel
import com.amalitech.bookmeetingroom.authentication_presentation.reset_password.ResetPasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authenticationPresentationModule = module {
    viewModel {
        LoginViewModel(get())
    }

    viewModel {
        ResetPasswordViewModel(get())
    }

    viewModel {
        ForgotPasswordViewModel(get())
    }
}
