package com.amalitech.bookmeetingroom.util

sealed class UiEvents {
    object NavigateToHome: UiEvents()
    data class ShowSnackBar(val text: UiText): UiEvents()

}
