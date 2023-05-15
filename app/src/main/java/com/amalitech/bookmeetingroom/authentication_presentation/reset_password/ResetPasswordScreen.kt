package com.amalitech.bookmeetingroom.authentication_presentation.reset_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.amalitech.bookmeetingroom.R
import com.amalitech.bookmeetingroom.authentication_presentation.components.AuthenticationTextField
import com.amalitech.bookmeetingroom.onboarding_presentation.components.DefaultButton
import com.amalitech.bookmeetingroom.ui.theme.LocalSpacing
import com.amalitech.bookmeetingroom.util.UiEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResetPasswordScreen(
    onNavigateToHome: () -> Unit
) {
    val viewModel: ResetPasswordViewModel = koinViewModel()
    val state = viewModel.state
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvents.NavigateToHome -> {
                    onNavigateToHome()
                }

                is UiEvents.showSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.text.asString(context)
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(spacing.medium)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logo),
                alignment = Alignment.TopCenter,
                colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(spacing.large))
            Text(
                text = stringResource(id = R.string.reset_password),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            if (state.error != null) {
                Spacer(modifier = Modifier.height(spacing.small))
                Text(
                    text = state.error.asString(context),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(spacing.large))

            AuthenticationTextField(
                placeholder = stringResource(R.string.new_password),
                value = state.newPassword,
                onValueChange = {
                    viewModel.onEvent(ResetPasswordEvent.OnNewPassword(it))
                },
                isPassword = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(spacing.medium))
            AuthenticationTextField(
                placeholder = stringResource(R.string.confirm_new_password),
                value = state.confirmNewPassword,
                onValueChange = {
                    viewModel.onEvent(ResetPasswordEvent.OnConfirmNewPassword(it))
                },
                isPassword = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(spacing.large))

            DefaultButton(
                text = stringResource(id = R.string.save_changes),
                onClick = { viewModel.onEvent(ResetPasswordEvent.OnSaveChangesClick) },
                modifier = Modifier
                    .fillMaxWidth()
            )

        }

    }

}

@Preview
@Composable
fun Prev() {
    ResetPasswordScreen {

    }
}
