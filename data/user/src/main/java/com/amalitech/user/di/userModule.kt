package com.amalitech.user.di

import com.amalitech.user.repository.UserRepository
import com.amalitech.user.repository.UserRepositoryImpl
import org.koin.dsl.module

val userModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
}