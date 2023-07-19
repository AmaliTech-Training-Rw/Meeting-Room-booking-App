package com.amalitech.core.util

data class ApiResult<T>(
    val data: T? = null,
    val error: UiText? = null
)
