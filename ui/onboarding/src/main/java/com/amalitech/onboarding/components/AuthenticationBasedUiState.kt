package com.amalitech.onboarding.components

import com.amalitech.core.util.UiText

data class AuthenticationBasedUiState(
    val error: UiText? = null,
    val snackBarValue: UiText? = null
)
