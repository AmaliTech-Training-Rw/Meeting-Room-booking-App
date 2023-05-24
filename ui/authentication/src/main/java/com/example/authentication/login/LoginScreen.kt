package com.example.authentication.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.ui.authentication.R
import com.example.authentication.components.AuthenticationTextField
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val loginUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val snackBarValue = loginUiState.snackBarValue
    val error = loginUiState.error
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = snackBarValue) {
        if (snackBarValue != null) {
            snackbarHostState.showSnackbar(
                message = snackBarValue.asString(context)
            )
            viewModel.onSnackBarShown()
        }
    }

    if (loginUiState.finishedLoggingIn) {
        onNavigateToHome()
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
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
                Text(
                    text = stringResource(id = R.string.log_into_account),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
                if (error != null) {
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    Text(
                        text = error.asString(context),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceLarge))
                AuthenticationTextField(
                    placeholder = stringResource(R.string.email),
                    value = loginUiState.email,
                    onValueChange = {
                        viewModel.onNewEmail(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    onGo = {
                        viewModel.onLoginClick()
                    }
                )
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                AuthenticationTextField(
                    placeholder = stringResource(R.string.password),
                    value = loginUiState.password,
                    onValueChange = {
                        viewModel.onNewPassword(it)
                    },
                    isPassword = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Go
                    ),
                    onGo = {
                        viewModel.onLoginClick()
                        keyboardController?.hide()
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
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            onNavigateToForgotPassword()
                        }
                    )
                }
                DefaultButton(
                    text = stringResource(id = R.string.sign_in),
                    onClick = { viewModel.onLoginClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing.spaceLarge)
                        .padding(vertical = spacing.spaceLarge)
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
        onNavigateToSignUp = {},
        onNavigateUp = {}
    )
}

@Composable
fun DefaultButton(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(size = spacing.spaceExtraSmall))
            .background(color = backgroundColor)
            .clickable {
                onClick()
            }
            .padding(spacing.spaceSmall),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor,
            textAlign = TextAlign.Center,
        )
    }
}
