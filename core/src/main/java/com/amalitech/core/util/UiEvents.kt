package com.amalitech.core.util

sealed class UiEvents {
    object NavigateToHome: UiEvents()
    object NavigateToLogin: UiEvents()
    data class ShowSnackBar(val text: UiText): UiEvents()

}
