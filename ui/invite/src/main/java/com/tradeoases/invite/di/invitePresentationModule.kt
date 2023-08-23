package com.tradeoases.invite.di

import com.tradeoases.invite.InvitesViewModel
import com.tradeoases.invite.usecases.GetInviteUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val invitePresentationModule = module {
    viewModel {
        InvitesViewModel(get())
    }
}