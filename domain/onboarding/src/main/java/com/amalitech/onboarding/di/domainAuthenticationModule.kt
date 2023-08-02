package com.amalitech.onboarding.di

import com.amalitech.onboarding.forgot_password.use_case.ForgotPasswordUseCasesWrapper
import com.amalitech.onboarding.forgot_password.use_case.SendResetLinkUseCase
import com.amalitech.onboarding.login.use_case.IsUserAdminUseCase
import com.amalitech.onboarding.login.use_case.LogInUseCase
import com.amalitech.onboarding.login.use_case.LoginUseCasesWrapper
import com.amalitech.onboarding.login.use_case.ValidatePasswordUseCase
import com.amalitech.onboarding.reset_password.CheckPasswordsMatchUseCase
import com.amalitech.onboarding.reset_password.ResetPasswordUseCase
import com.amalitech.onboarding.reset_password.ResetPasswordUseCasesWrapper
import com.amalitech.onboarding.signup.use_case.CheckValuesNotBlankUseCase
import com.amalitech.onboarding.signup.use_case.FetchOrganizationsTypeUseCase
import com.amalitech.onboarding.signup.use_case.IsEmailAvailableUseCase
import com.amalitech.onboarding.signup.use_case.IsUsernameAvailableUseCase
import com.amalitech.onboarding.signup.use_case.SignupUseCase
import com.amalitech.onboarding.signup.use_case.SignupUseCasesWrapper
import org.koin.dsl.module

val onboardingDomainModule = module {
    single {
        LogInUseCase()
    }

    single {
        ValidatePasswordUseCase()
    }

    single {
        SendResetLinkUseCase()
    }

    single {
        LoginUseCasesWrapper(
            get(),
            get(),
            get(),
            get()
        )
    }

    single {
        CheckPasswordsMatchUseCase()
    }

    single {
        ResetPasswordUseCase()
    }

    single {
        ResetPasswordUseCasesWrapper(get(), get(), get())
    }

    single {
        ForgotPasswordUseCasesWrapper(
            get(),
            get()
        )
    }

    single {
        IsUserAdminUseCase()
    }

    single {
        IsEmailAvailableUseCase()
    }

    single {
        IsUsernameAvailableUseCase()
    }

    single {
        SignupUseCase()
    }

    single {
        FetchOrganizationsTypeUseCase()
    }

    single {
        CheckValuesNotBlankUseCase()
    }

    single {
        SignupUseCasesWrapper(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}
