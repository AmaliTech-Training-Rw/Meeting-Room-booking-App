package com.amalitech.onboarding.components

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface AuthenticationBaseViewModel{
    val basedUiState: MutableStateFlow<AuthenticationBasedUiState>
    var job: Job?

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