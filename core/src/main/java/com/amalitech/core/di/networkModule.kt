package com.amalitech.core.di

import com.amalitech.core.network.BookMeetingNetworkApi
import com.amalitech.core.util.ApiConstants.BASE_URL
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {

    single<OkHttpClient> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(get()))
            .addInterceptor(
                interceptor
            ).connectTimeout(160, TimeUnit.SECONDS).readTimeout(160, TimeUnit.SECONDS)
            .writeTimeout(160, TimeUnit.SECONDS).retryOnConnectionFailure(true).build()
    }

    single<BookMeetingNetworkApi> {
        val moshi = Moshi.Builder().build()
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            ).build()
            .create(BookMeetingNetworkApi::class.java)
    }
}
