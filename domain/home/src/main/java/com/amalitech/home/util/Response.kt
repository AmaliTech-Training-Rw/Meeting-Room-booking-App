package com.amalitech.home.util

import com.amalitech.core.util.UiText

data class Response<T>(
    val data: T? = null,
    val error: UiText? = null
)
