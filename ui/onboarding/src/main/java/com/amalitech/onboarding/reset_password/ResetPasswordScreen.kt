package com.amalitech.onboarding.reset_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.core.R
import com.amalitech.core_ui.components.DefaultButton
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.onboarding.components.AuthenticationTextField
import com.amalitech.core_ui.util.UiState
import com.amalitech.onboarding.util.showSnackBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val baseResult
            by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = baseResult) {
        when (baseResult) {
            is UiState.Success -> {
                onNavigateToLogin()
            }

            is UiState.Error -> {
                showSnackBar(
                    snackBarValue = (baseResult as UiState.Error<ResetPasswordUiState>).error,
                    snackbarHostState = snackbarHostState,
                    context = context
                ) {
                    viewModel.onSnackBarShown()
                }
            }

            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(spacing.spaceMedium)
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
                ),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(spacing.spaceLarge))
            Text(
                text = stringResource(id = R.string.reset_password),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
            AuthenticationTextField(
                placeholder = stringResource(R.string.new_password),
                value = state.newPassword,
                onValueChange = {
                    viewModel.onNewPassword(it)
                },
                isPassword = true,
                modifier = Modifier.fillMaxWidth(),
                onGo = {
                    viewModel.onResetPassword()
                }
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            AuthenticationTextField(
                placeholder = stringResource(R.string.confirm_new_password),
                value = state.passwordConfirmation,
                onValueChange = {
                    viewModel.onNewPasswordConfirmation(it)
                },
                isPassword = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Go,
                    keyboardType = KeyboardType.Password
                ),
                onGo = {
                    viewModel.onResetPassword()
                }
            )
            Spacer(Modifier.height(spacing.spaceLarge))
            DefaultButton(
                text = stringResource(id = R.string.save_changes),
                onClick = { viewModel.onResetPassword() },
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = baseResult !is UiState.Loading
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
