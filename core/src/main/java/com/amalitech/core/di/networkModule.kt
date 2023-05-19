package com.amalitech.core.di

import android.util.Log
import com.amalitech.core.network.BookMeetingNetworkApi
import com.amalitech.core.util.ApiConstants.BASE_URL
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


object NetworkModule {

    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                Interceptor { chain ->
                    val ongoing: Request.Builder = chain.request().newBuilder()
                    ongoing.addHeader(
                        "Accept",
                        "application/json"
                    )
                    ongoing.addHeader(
                        "Content-Type",
                        "application/x-www-form-urlencoded"
                    )
                    chain.proceed(ongoing.build())
                }
            )
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(160, TimeUnit.SECONDS)
            .readTimeout(160, TimeUnit.SECONDS)
            .writeTimeout(160, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverter(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(
        loggingInterceptor: LoggingInterceptor
    ): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(loggingInterceptor)
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): BookMeetingNetworkApi {
        return retrofit.create(BookMeetingNetworkApi::class.java)
    }
}

class LoggingInterceptor @Inject constructor() : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        // TODO 2: replace with timber
        Log.d("LoggingInterceptor", "log: $message")
    }
}