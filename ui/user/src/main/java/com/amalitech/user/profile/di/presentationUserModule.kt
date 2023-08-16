package com.amalitech.user.profile.di

import com.amalitech.user.profile.ProfileViewModel
import com.amalitech.user.profile.update_profile.UpdateProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationUserModule = module {
    viewModel {
        ProfileViewModel(get(), get())
    }

    viewModel {
        UpdateProfileViewModel(get())
    }
}
