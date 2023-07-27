package com.amalitech.core.di

import com.amalitech.core.data.BookMeetingRepositoryImpl
import com.amalitech.core.domain.BookMeetingRepository
import com.amalitech.core.domain.use_case.ValidateEmailUseCase
import org.koin.dsl.module

val coreModule = module {
    single<BookMeetingRepository> {
        BookMeetingRepositoryImpl(get())
    }

    single {
        ValidateEmailUseCase()
    }
}
