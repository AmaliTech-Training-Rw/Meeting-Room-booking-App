package com.amalitech.onboarding.forgot_password

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
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
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
fun ForgotPasswordScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToReset: () -> Unit,
    viewModel: ForgotPasswordViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val baseResult by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = baseResult) {
        when (baseResult) {
            is UiState.Success -> {
                onNavigateToReset()
            }
            is UiState.Error -> {
                showSnackBar(
                    snackBarValue = (baseResult as UiState.Error<ForgotPasswordUiState>).error,
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                text = stringResource(id = R.string.forgot_password),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
            AuthenticationTextField(
                placeholder = stringResource(R.string.email),
                value = state.email,
                onValueChange = {
                    viewModel.onNewEmail(it)
                },
                isPassword = false,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Go
                ),
                onGo = {
                    viewModel.onSendResetLink()
                }
            )

            Spacer(modifier = Modifier.height(spacing.spaceLarge))
            DefaultButton(
                text = stringResource(id = R.string.send_reset_link),
                onClick = { viewModel.onSendResetLink() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = spacing.spaceSmall),
                enabled = baseResult !is UiState.Loading
            )
            val text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append(stringResource(id = R.string.question_already_have_an_account))
                    append(" ")
                }
                pushStringAnnotation(
                    tag = "URL",
                    annotation = stringResource(id = R.string.log_in)
                )
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.outline,
                    )
                ) {
                    append(stringResource(id = R.string.log_in))
                }
                pop()
            }
            ClickableText(
                text = text,
                onClick = { offset ->
                    val url = text.getStringAnnotations("URL", offset, offset).firstOrNull()?.item
                    if (!url.isNullOrEmpty()) {
                        onNavigateToLogin()
                    }
                },
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    ForgotPasswordScreen(onNavigateToLogin = {}, onNavigateToReset = {})
}
