package com.amalitech.onboarding.util

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import com.amalitech.onboarding.components.AuthenticationBasedUiState

suspend fun showSnackBar(
    state: AuthenticationBasedUiState,
    snackbarHostState: SnackbarHostState,
    context: Context,
    onSnackBarShown: () -> Unit
) {
    state.snackBarValue?.let {
        snackbarHostState.showSnackbar(
            it.asString(context = context)
        )
        onSnackBarShown()
    }

}
