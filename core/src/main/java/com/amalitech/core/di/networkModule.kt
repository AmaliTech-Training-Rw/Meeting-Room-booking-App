package com.amalitech.core.di

import com.amalitech.core.network.BookMeetingNetworkApi
import com.amalitech.core.util.ApiConstants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(get()))
            .addInterceptor(interceptor)
            .connectTimeout(160, TimeUnit.SECONDS).readTimeout(160, TimeUnit.SECONDS)
            .writeTimeout(160, TimeUnit.SECONDS).retryOnConnectionFailure(true).build()
    }


    single {
        MoshiConverterFactory.create()
    }

    single {
        Retrofit.Builder().baseUrl(BASE_URL).client(get()).addConverterFactory(get()).build()
            .create(BookMeetingNetworkApi::class.java)
    }
}
