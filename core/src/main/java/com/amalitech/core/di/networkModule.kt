package com.amalitech.core.di

import com.amalitech.core.network.BookMeetingNetworkApi
import com.amalitech.core.util.ApiConstants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


val networkModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(get()))
            .connectTimeout(160, TimeUnit.SECONDS).readTimeout(160, TimeUnit.SECONDS)
            .writeTimeout(160, TimeUnit.SECONDS).retryOnConnectionFailure(true).build()
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder().baseUrl(BASE_URL).client(get()).addConverterFactory(get()).build()
            .create(BookMeetingNetworkApi::class.java)
    }
}
