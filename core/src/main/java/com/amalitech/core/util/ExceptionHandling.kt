package com.amalitech.core.util

import com.amalitech.core.R

fun Exception.extractError(): UiText {
    localizedMessage?.let { return UiText.DynamicString(it) }
    return UiText.StringResource(R.string.error_default_message)
}
