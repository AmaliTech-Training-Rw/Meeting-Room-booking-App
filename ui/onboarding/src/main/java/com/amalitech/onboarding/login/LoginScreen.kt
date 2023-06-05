package com.amalitech.onboarding.login

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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.core_ui.components.DefaultButton
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.onboarding.components.AuthenticationTextField
import com.amalitech.core.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val onGo = {
        viewModel.onLoginClick()
        keyboardController?.hide()
    }

    LaunchedEffect(key1 = state) {
        state.snackBarValue?.let {
            snackbarHostState.showSnackbar(
                it.asString(context = context)
            )
            viewModel.onSnackBarShown()
        }
        if (state.finishedLoggingIn) {
            onNavigateToHome()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(spacing.spaceMedium)
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
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.size(
                        spacing.spaceExtraLarge
                    )
                )
                Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
                Text(
                    text = stringResource(id = R.string.log_into_account),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp
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
                    modifier = Modifier.fillMaxWidth(),
                    onGo = {
                        onGo()
                    }
                )
                Spacer(modifier = Modifier.height(spacing.spaceLarge))
                AuthenticationTextField(
                    placeholder = stringResource(R.string.password),
                    value = state.password,
                    onValueChange = {
                        viewModel.onNewPassword(it)
                    },
                    isPassword = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Go,
                        keyboardType = KeyboardType.Password
                    ),
                    onGo = {
                        onGo()
                    }
                )
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = stringResource(R.string.question_forgot_password),
                        textAlign = TextAlign.Right,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.clickable {
                            onNavigateToForgotPassword()
                        }
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
                DefaultButton(
                    text = stringResource(id = R.string.sign_in),
                    onClick = { onGo() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = spacing.spaceLarge)
                        .padding(bottom = spacing.spaceLarge)
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
                        color = MaterialTheme.colorScheme.outline,
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
                    .padding(vertical = spacing.spaceLarge)
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
        onNavigateToSignUp = {}
    )
}
