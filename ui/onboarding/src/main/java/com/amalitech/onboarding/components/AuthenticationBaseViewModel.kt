package com.amalitech.onboarding.components

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface AuthenticationBaseViewModel{
    val basedUiState: MutableStateFlow<AuthenticationBasedUiState>

    /**
     * onSnackBarShown - Reset snackBarValue to null
     */
    fun onSnackBarShown() {
        basedUiState.update { state ->
            state.copy(
                snackBarValue = null
            )
        }
    }
}