package com.amalitech.core.di

import android.util.Log
import com.amalitech.core.data.BookMeetingRepositoryImpl
import com.amalitech.core.domain.BookMeetingRepository
import com.amalitech.core.network.BookMeetingNetworkApi
import com.amalitech.core.util.ApiConstants
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val dataModule = module {
    singleOf(::BookMeetingRepositoryImpl) { bind<BookMeetingRepository>() }
}

val networkModule = module {
    factoryOf(::provideHttpLoggingInterceptor)
    factoryOf(::provideApiService)
    factoryOf(::provideHttpClient)
    factoryOf(::provideConverter)
    single { provideRetrofitClient(get(), get()) }
}

// add view-models/ fragments and so on here for reference, check https://github.com/InsertKoinIO/koin-getting-started
val appModule = module {
    // e.g
    // factoryOf(::UserStateHolder)
    // viewModelOf(::UserViewModel)
    // etc
}

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

fun provideConverter(): MoshiConverterFactory {
    return MoshiConverterFactory.create()
}

fun provideRetrofitClient(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: MoshiConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory).build()
}

fun provideHttpLoggingInterceptor(
    loggingInterceptor: LoggingInterceptor
): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor(loggingInterceptor)
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}

fun provideApiService(retrofit: Retrofit): BookMeetingNetworkApi {
    return retrofit.create(BookMeetingNetworkApi::class.java)
}

class LoggingInterceptor : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        // TODO 2: replace with timber
        Log.d("LoggingInterceptor", "log: $message")
    }
}