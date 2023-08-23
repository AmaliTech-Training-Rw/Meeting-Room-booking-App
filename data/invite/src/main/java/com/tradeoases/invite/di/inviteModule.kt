package com.tradeoases.invite.di

import com.tradeoases.invite.repository.InviteRepository
import com.tradeoases.invite.repository.InviteRepositoryImpl
import org.koin.dsl.module

val inviteModule = module {
    single<InviteRepository> {
        InviteRepositoryImpl()
    }
}