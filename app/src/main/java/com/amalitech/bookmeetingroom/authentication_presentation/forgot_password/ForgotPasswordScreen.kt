package com.amalitech.bookmeetingroom.authentication_presentation.forgot_password

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import com.amalitech.bookmeetingroom.R
import com.amalitech.bookmeetingroom.authentication_presentation.components.AuthenticationTextField
import com.amalitech.bookmeetingroom.onboarding_presentation.components.DefaultButton
import com.amalitech.bookmeetingroom.ui.theme.LocalSpacing
import com.amalitech.bookmeetingroom.util.UiEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgotPasswordScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val viewModel: ForgotPasswordViewModel = koinViewModel()
    val state = viewModel.state
    val spacing = LocalSpacing.current
    val context = LocalContext.current
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                text = stringResource(id = R.string.forgot_password),
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
                placeholder = stringResource(R.string.email),
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(ForgotPasswordEvent.OnNewEmail(it))
                },
                isPassword = false,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Go
                ),
                onGo = {
                    viewModel.onEvent(ForgotPasswordEvent.OnSendResetLink)
                }
            )

            DefaultButton(
                text = stringResource(id = R.string.send_reset_link),
                onClick = { viewModel.onEvent(ForgotPasswordEvent.OnSendResetLink) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = spacing.small)
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
                    // check if clicked text has "URL" tag
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
