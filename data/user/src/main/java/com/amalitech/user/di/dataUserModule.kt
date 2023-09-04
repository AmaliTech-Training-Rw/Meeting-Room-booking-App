package com.amalitech.user.di

import androidx.room.Room
import com.amalitech.core.data.BASE_URL
import com.amalitech.user.data_source.local.UserDatabase
import com.amalitech.user.data_source.remote.UserApiService
import com.amalitech.user.repository.UserRepository
import com.amalitech.user.repository.UserRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val dataUserModule = module {
    factory {
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java,
            UserDatabase.DATABASE_NAME
        ).build()
    }

    single {
        get<UserDatabase>().userDao()
    }

    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }

    single <UserApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
            .create()
    }
}
