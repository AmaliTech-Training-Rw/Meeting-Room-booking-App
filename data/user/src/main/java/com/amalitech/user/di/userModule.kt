package com.amalitech.user.di

import com.amalitech.user.UserRepositoryImpl
import com.amalitech.user.repository.UserRepository
import org.koin.dsl.module

val userModule = module {
    single<UserRepository> {
        UserRepositoryImpl()
    }
}