package com.tradeoases.invite.di

import com.tradeoases.invite.usecases.GetInviteUseCase
import org.koin.dsl.module

val domainInviteModule = module {
    single {
        GetInviteUseCase(get())
    }
}
