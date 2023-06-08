package com.amalitech.onboarding.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.amalitech.core.R
import com.amalitech.core_ui.components.DefaultButton
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.onboarding.components.AuthenticationDropDown
import com.amalitech.onboarding.components.AuthenticationTextField
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignupScreen(
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel = koinViewModel(),
    navBackStackEntry: NavBackStackEntry
) {
    val arguments = navBackStackEntry.arguments
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val organizationName = arguments?.getString(NavArguments.organizationName)
    val email = arguments?.getString(NavArguments.email)
    val typeOfOrganization = arguments?.getString(NavArguments.typeOfOrganization)
    val location = arguments?.getString(NavArguments.location)
    val invitedUser = viewModel.isInvitedUser(email, organizationName, location, typeOfOrganization)
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    var isDropDownExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    val focusManager: FocusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val organizationType = state.typeOfOrganization
    val selectedItem = state.selectedOrganizationType

    val onGo = {
        viewModel.onSignupClick()
        keyboardController?.hide()
    }

    LaunchedEffect(key1 = state) {
        state.snackBarValue?.let {
            snackbarHostState.showSnackbar(
                it.asString(context = context)
            )
            viewModel.onSnackBarShown()
        }
        if (state.finishedSigningUp) {
            onNavigateToLogin()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .padding(
                    vertical = spacing.spaceMedium,
                    horizontal = spacing.spaceLarge
                )
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(R.string.logo),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier.size(
                    spacing.spaceExtraLarge
                )
            )
            Spacer(modifier = Modifier.height(spacing.spaceLarge))
            val header = if (!invitedUser) {
                stringResource(id = R.string.create_your_account)
            } else {
                stringResource(id = R.string.get_started)
            }
            Text(
                text = header,
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
                onGo = { onGo() },
                placeholder = stringResource(id = R.string.username),
                value = state.username,
                onValueChange = {
                    viewModel.onNewUsername(it)
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (!invitedUser) {
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                AuthenticationTextField(
                    onGo = { onGo() },
                    placeholder = stringResource(id = R.string.organization_name),
                    value = state.organizationName,
                    onValueChange = {
                        viewModel.onNewOrganizationName(it)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                AuthenticationTextField(
                    onGo = { onGo() },
                    placeholder = stringResource(id = R.string.email),
                    value = state.email,
                    onValueChange = {
                        viewModel.onNewEmail(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email
                    )
                )
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                AuthenticationDropDown(
                    isDropDownExpanded = isDropDownExpanded,
                    items = organizationType,
                    onSelectedItemChange = {
                        viewModel.onSelectedOrganizationType(it)
                                           },
                    onIsExpandedStateChange = { isDropDownExpanded = it },
                    selectedItem = selectedItem,
                    focusManager = focusManager
                ) { isDropDownExpanded = it }
                Spacer(Modifier.height(spacing.spaceSmall))
                AuthenticationTextField(
                    onGo = { onGo() },
                    placeholder = stringResource(id = R.string.location),
                    value = state.location,
                    onValueChange = {
                        viewModel.onNewLocation(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            } else {
                viewModel.validateArguments(
                    organizationName,
                    typeOfOrganization,
                    location,
                    email
                )
            }
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            AuthenticationTextField(
                onGo = { onGo() },
                placeholder = stringResource(id = R.string.password),
                value = state.password,
                onValueChange = {
                    viewModel.onNewPassword(it)
                },
                modifier = Modifier.fillMaxWidth(),
                isPassword = true
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            AuthenticationTextField(
                onGo = { onGo() },
                placeholder = stringResource(id = R.string.confirm_new_password),
                value = state.passwordConfirmation,
                onValueChange = {
                    viewModel.onNewPasswordConfirmation(it)
                },
                modifier = Modifier.fillMaxWidth(),
                isPassword = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Go,
                    keyboardType = KeyboardType.Password
                )
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            val buttonText = if (!invitedUser) {
                stringResource(id = R.string.sign_up)
            } else {
                stringResource(id = R.string.set_up)
            }
            DefaultButton(
                text = buttonText,
                onClick = { onGo() },
                modifier = Modifier.fillMaxWidth()
            )
            if (!invitedUser) {
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
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
                        val url =
                            text.getStringAnnotations("URL", offset, offset).firstOrNull()?.item
                        if (!url.isNullOrEmpty()) {
                            onNavigateToLogin()
                        }
                    },
                    modifier = Modifier
                )
            }
        }
    }
}
