package com.amalitech.core.util

data class Response<T>(
    val data: T? = null,
    val error: UiText? = null
)
