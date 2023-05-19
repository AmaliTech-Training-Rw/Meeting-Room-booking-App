package com.amalitech.core.di

import com.amalitech.core.data.BookMeetingRepositoryImpl
import com.amalitech.core.domain.BookMeetingRepository
import org.koin.dsl.module

val coreModule = module {
  single<BookMeetingRepository> {
    BookMeetingRepositoryImpl(get())
  }
}
