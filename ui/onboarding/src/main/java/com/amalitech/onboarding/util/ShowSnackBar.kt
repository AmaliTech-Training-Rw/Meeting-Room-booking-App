package com.amalitech.onboarding.util

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import com.amalitech.onboarding.components.AuthenticationBaseViewModel
import com.amalitech.onboarding.components.AuthenticationBasedUiState

suspend fun showSnackBar(
    state: AuthenticationBasedUiState,
    snackbarHostState: SnackbarHostState,
    context: Context,
    viewModel: AuthenticationBaseViewModel
) {
    state.snackBarValue?.let {
        snackbarHostState.showSnackbar(
            it.asString(context = context)
        )
        viewModel.onSnackBarShown()
    }

}
