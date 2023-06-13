package com.amalitech.core_ui.util

import com.amalitech.core.util.UiText

sealed class UiState<T> {
    data class Success<T>(
        val data: T? = null
    ) : UiState<T>()

    data class Error<T>(val error: UiText?) : UiState<T>()
    class Loading<T> : UiState<T>()
}
