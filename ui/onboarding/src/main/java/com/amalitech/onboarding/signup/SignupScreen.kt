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
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.BookMeetingRoomDropDown
import com.amalitech.core_ui.components.DefaultButton
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.UiState
import com.amalitech.core_ui.components.AuthenticationTextField
import com.amalitech.core.domain.model.LocationX
import com.amalitech.onboarding.signup.model.TypesOrganisation
import com.amalitech.onboarding.util.showSnackBar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignupScreen(
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry,
    snackbarHostState: SnackbarHostState,
    viewModel: SignupViewModel = koinViewModel(),
    onComposing: (AppBarState) -> Unit
) {
    val arguments = navBackStackEntry.arguments
    val userInput by viewModel.userInput
    val token = arguments?.getString(NavArguments.token)
    val invitedUser = viewModel.isInvitedUser(token)
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    var isOrganizationDropDownExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    var isLocationDropDownExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    val focusManager: FocusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val selectedOrganization = userInput.selectedOrganizationType
    var organizationType: List<TypesOrganisation> by remember {
        mutableStateOf(listOf())
    }
    var locations: List<LocationX> by remember {
        mutableStateOf(listOf())
    }
    val selectedLocation = userInput.location
    val onGo = {
        viewModel.onSignupClick()
        keyboardController?.hide()
    }

    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            is UiState.Success -> {
                (uiState as UiState.Success<SignupUiState>).data?.let {
                    if (it.shouldNavigate) {
                        onNavigateToLogin()
                    }
                    organizationType = it.typeOfOrganization
                    locations = it.locations
                }
            }

            is UiState.Error -> {
                showSnackBar(
                    snackBarValue = (uiState as UiState.Error<SignupUiState>).error,
                    snackbarHostState = snackbarHostState,
                    context = context
                ) {
                    viewModel.onSnackBarShown()
                }
            }

            else -> {}
        }
    }
    LaunchedEffect(key1 = true) {
        onComposing(AppBarState(hasTopBar = false))
    }

    Column(
        modifier = modifier
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
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        AuthenticationTextField(
            onGo = { onGo() },
            placeholder = stringResource(id = R.string.username),
            value = userInput.username,
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
                value = userInput.organizationName,
                onValueChange = {
                    viewModel.onNewOrganizationName(it)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            AuthenticationTextField(
                onGo = { onGo() },
                placeholder = stringResource(id = R.string.email),
                value = userInput.email,
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
            BookMeetingRoomDropDown(
                isDropDownExpanded = isOrganizationDropDownExpanded,
                items = organizationType.map { it.name },
                onSelectedItemChange = {
                    val id = organizationType.find { type ->
                        type.name == it
                    }?.id ?: -1
                    viewModel.onSelectedOrganizationType(id)
                },
                onIsExpandedStateChange = { isOrganizationDropDownExpanded = it },
                selectedItem = organizationType.find { selectedOrganization == it.id }?.name ?: "",
                focusManager = focusManager,
                R.string.type_of_organization,
            ) { isOrganizationDropDownExpanded = it }
            Spacer(Modifier.height(spacing.spaceSmall))
            BookMeetingRoomDropDown(
                isDropDownExpanded = isLocationDropDownExpanded,
                items = locations.map { it.name },
                onSelectedItemChange = {
                    val id = locations.find { type ->
                        type.name == it
                    }?.id ?: -1
                    viewModel.onLocationSelected(id)
                },
                onIsExpandedStateChange = { isLocationDropDownExpanded = it },
                selectedItem = locations.find { selectedLocation == it.id }?.name ?: "",
                focusManager = focusManager,
                R.string.location,
            ) { isLocationDropDownExpanded = it }
        } else {
            if (token != null) {
                viewModel.submitValues(token)
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        AuthenticationTextField(
            onGo = { onGo() },
            placeholder = stringResource(id = R.string.password),
            value = userInput.password,
            onValueChange = {
                viewModel.onNewPassword(it)
            },
            modifier = Modifier.fillMaxWidth(),
            isPassword = true
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        AuthenticationTextField(
            onGo = { onGo() },
            placeholder = stringResource(id = R.string.confirm_password),
            value = userInput.passwordConfirmation,
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
            modifier = Modifier.fillMaxWidth(),
            isLoading = uiState is UiState.Loading
        )
        if (!invitedUser) {
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            val text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append(stringResource(id = R.string.question_already_have_an_account))
                    append(" ")
                }
                pushStringAnnotation(
                    tag = "URL",
                    annotation = stringResource(id = R.string.log_in)
                )
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.scrim,
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
