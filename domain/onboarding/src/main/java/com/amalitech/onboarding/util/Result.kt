package com.amalitech.onboarding.util

import com.amalitech.core.util.UiText

sealed class Result<out T> {
    data class Success<out T>(val data: List<T>) : Result<T>()
    data class Error(val errorText: UiText) : Result<Nothing>()
    object Loading: Result<Nothing>()
}
