package com.amalitech.bookmeetingroom.authentication_presentation.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.amalitech.bookmeetingroom.R
import com.amalitech.bookmeetingroom.authentication_presentation.components.AuthenticationTextField
import com.amalitech.bookmeetingroom.onboarding_presentation.components.DefaultButton
import com.amalitech.bookmeetingroom.ui.theme.LocalSpacing
import com.amalitech.bookmeetingroom.util.UiEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val viewModel: LoginViewModel = koinViewModel()
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

    BackHandler {
        onNavigateUp()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(spacing.medium)
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(R.string.logo),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.height(spacing.extraLarge))
                Text(
                    text = stringResource(id = R.string.log_into_account),
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
                        viewModel.onEvent(LoginEvent.OnNewEmail(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(spacing.small))
                AuthenticationTextField(
                    placeholder = stringResource(R.string.password),
                    value = state.password,
                    onValueChange = {
                        viewModel.onEvent(LoginEvent.OnNewPassword(it))
                    },
                    isPassword = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(spacing.medium))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = stringResource(R.string.question_forgot_password),
                        textAlign = TextAlign.Right,
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            onNavigateToForgotPassword()
                        }
                    )
                }
                DefaultButton(
                    text = stringResource(id = R.string.sign_in),
                    onClick = { viewModel.onEvent(LoginEvent.OnLoginClick) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing.large)
                        .padding(vertical = spacing.large)
                )
            }
            val text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append(stringResource(id = R.string.question_dont_have_account))
                    append(" ")
                }
                pushStringAnnotation(
                    tag = "URL",
                    annotation = stringResource(id = R.string.sign_up)
                )
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                    )
                ) {
                    append(stringResource(id = R.string.sign_up))
                }
                pop()
            }
            ClickableText(
                text = text,
                onClick = { offset ->
                    // check if clicked text has "URL" tag
                    val url = text.getStringAnnotations("URL", offset, offset).firstOrNull()?.item
                    if (!url.isNullOrEmpty()) {
                        onNavigateToSignUp()
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = spacing.large)
            )

        }
    }
}
@Preview
@Composable
fun Prev() {
    LoginScreen(
        onNavigateToHome = {},
        onNavigateToForgotPassword = {},
        onNavigateToSignUp = {},
        onNavigateUp = {}
    )
}
