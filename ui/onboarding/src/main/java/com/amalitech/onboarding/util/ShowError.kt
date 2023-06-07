package com.amalitech.onboarding.util

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.amalitech.core_ui.theme.Dimensions
import com.amalitech.onboarding.components.AuthenticationBasedUiState

@Composable
fun ShowError(
    state: AuthenticationBasedUiState,
    spacing: Dimensions,
    context: Context
) {
    state.error?.let {
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Text(
            text = it.asString(context),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
