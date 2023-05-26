package com.example.onboarding_presentation.forgot_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.ui.onboarding.R
import com.example.onboarding_presentation.components.DefaultButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgotPasswordScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: ForgotPasswordViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = state) {
        state.snackBarValue?.let {
            snackbarHostState.showSnackbar(
                it.asString(context)
            )
            viewModel.onSnackBarShown()
        }

        if (state.linkSent) {
            onNavigateToHome()
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
                painter = painterResource(id = com.amalitech.core.R.drawable.logo),
                contentDescription = stringResource(id = com.amalitech.core.R.string.logo),
                alignment = Alignment.TopCenter,
                colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.height(spacing.spaceLarge))
            Text(
                text = stringResource(id = R.string.forgot_password),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            state.error?.let {
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                Text(
                    text = it.asString(context),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(spacing.spaceLarge))

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

            DefaultButton(
                text = stringResource(id = R.string.send_reset_link),
                onClick = { viewModel.onSendResetLink() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing.spaceSmall)
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
                        color = Color.Blue,
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
