package com.amalitech.onboarding.util

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import com.amalitech.core.util.UiText

suspend fun showSnackBar(
    snackBarValue: UiText?,
    snackbarHostState: SnackbarHostState,
    context: Context,
    onSnackBarShown: () -> Unit
) {
    snackBarValue?.let {
        snackbarHostState.showSnackbar(
            it.asString(context = context)
        )
        onSnackBarShown()
    }

}
