package com.amalitech.core.di

import android.util.Log
import com.amalitech.core.domain.preferences.OnboardingSharedPreferences
import com.amalitech.core.util.ApiParameters.AUTH_HEADER
import com.amalitech.core.util.ApiParameters.TOKEN_TYPE
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthenticationInterceptor constructor(
    private val preferences: OnboardingSharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.i("INTERCEPTOR", "${chain.request()}")
        return chain.proceed(chain.createAuthenticatedRequest(preferences.loadToken()))
    }
    private fun Interceptor.Chain.createAuthenticatedRequest(token: String): Request {
        return request()
            .newBuilder()
            .addHeader(AUTH_HEADER, TOKEN_TYPE + token)
            .addHeader(
                "Accept", "application/json"
            )
            .addHeader(
                "Content-Type", "application/json"
            )
            .build()
    }
}