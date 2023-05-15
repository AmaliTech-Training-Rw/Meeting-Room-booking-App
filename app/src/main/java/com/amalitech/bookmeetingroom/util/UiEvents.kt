package com.amalitech.bookmeetingroom.util

sealed class UiEvents {
    object NavigateToHome: UiEvents()
    data class showSnackBar(val text: UiText): UiEvents()

}
