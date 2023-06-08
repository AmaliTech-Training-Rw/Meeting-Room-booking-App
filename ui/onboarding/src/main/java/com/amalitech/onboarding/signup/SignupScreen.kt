@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.amalitech.onboarding.signup

import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.amalitech.core_ui.components.DefaultButton
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.onboarding.components.AuthenticationDropDown
import com.amalitech.onboarding.components.AuthenticationTextField
import com.amalitech.onboarding.util.Result
import com.amalitech.core.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignupScreen(
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel = koinViewModel(),
    navBackStackEntry: NavBackStackEntry
) {
    val arguments = navBackStackEntry.arguments
    val state by viewModel.uiState.collectAsStateWithLifecycle()
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
    var selectedItem by when (organizationType) {
        is Result.Success -> rememberSaveable {
            mutableStateOf(organizationType.data.firstOrNull() ?: "")
        }

        else -> rememberSaveable {
            mutableStateOf("")
        }
    }

    val onGo = {
        viewModel.onSelectedOrganizationType(selectedItem)
        viewModel.onSignupClick()
        keyboardController?.hide()
    }

    val organizationName = arguments?.getString(NavArguments.organizationName)
    val email = arguments?.getString(NavArguments.email)
    val typeOfOrganization = arguments?.getString(NavArguments.typeOfOrganization)
    val location = arguments?.getString(NavArguments.location)

    LaunchedEffect(key1 = state) {
        state.snackBarValue?.let {
            snackbarHostState.showSnackbar(
                it.asString(context = context)
            )
            viewModel.onSnackBarShown()
        }
    }

    if (state.finishedSigningUp) {
        onNavigateToLogin()
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
            val header = if (arguments == null) {
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
            if (arguments == null) {
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
                Box(Modifier.wrapContentSize(Alignment.TopStart)) {
                    TextField(
                        value = selectedItem,
                        onValueChange = {
                            selectedItem = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(1.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(spacing.spaceExtraSmall)
                            )
                            .padding(spacing.spaceExtraSmall)
                            .onPreviewKeyEvent {
                                if (it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                                    focusManager.moveFocus(FocusDirection.Down)
                                    true
                                } else {
                                    false
                                }
                            },
                        placeholder = {
                            Text(stringResource(R.string.type_of_organization))
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        enabled = false,
                        trailingIcon = {
                            val image = if (isDropDownExpanded)
                                com.amalitech.ui.onboarding.R.drawable.baseline_arrow_drop_up_24
                            else
                                com.amalitech.ui.onboarding.R.drawable.baseline_arrow_drop_down_24
                            val description = if (isDropDownExpanded)
                                stringResource(id = R.string.close_organization_type)
                            else
                                stringResource(id = R.string.open_organization_type)

                            IconButton(onClick = { isDropDownExpanded = !isDropDownExpanded }) {
                                Icon(
                                    painter = painterResource(id = image),
                                    contentDescription = description
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            disabledTextColor = MaterialTheme.colorScheme.onBackground,
                            disabledContainerColor = MaterialTheme.colorScheme.background,
                            disabledIndicatorColor = Color.Transparent
                        ),
                    )
                    AuthenticationDropDown(
                        isDropDownExpanded,
                        organizationType,
                        onSelectedItemChange = {
                            selectedItem = it
                            viewModel.onSelectedOrganizationType(it)
                        },
                        onRetry = { viewModel.fetchOrganizations() },
                        onIsExpandedStateChange = {
                            isDropDownExpanded = it
                        }
                    )
                }
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
            val buttonText = if (arguments == null) {
                stringResource(id = R.string.sign_up)
            } else {
                stringResource(id = R.string.set_up)
            }
            DefaultButton(
                text = buttonText,
                onClick = { onGo() },
                modifier = Modifier.fillMaxWidth()
            )
            if (arguments == null) {
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
