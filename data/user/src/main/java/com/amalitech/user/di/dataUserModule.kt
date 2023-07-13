package com.amalitech.user.di

import androidx.room.Room
import com.amalitech.user.data_source.local.UserDatabase
import com.amalitech.user.repository.UserRepository
import com.amalitech.user.repository.UserRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataUserModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java,
            UserDatabase.DATABASE_NAME
        ).build().userDao()
    }

    single<UserRepository> {
        UserRepositoryImpl(get())
    }
}
