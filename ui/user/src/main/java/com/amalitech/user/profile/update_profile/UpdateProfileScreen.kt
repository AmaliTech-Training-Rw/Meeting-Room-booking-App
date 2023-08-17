package com.amalitech.user.profile.update_profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.AuthenticationTextField
import com.amalitech.core_ui.components.DefaultButton
import com.amalitech.core_ui.components.NavigationButton
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.ui.user.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpdateProfileScreen(
    email: String,
    viewModel: UpdateProfileViewModel = koinViewModel(),
    showSnackBar: (message: String) -> Unit,
    onNavigateBack: () -> Unit,
    onComposing: (AppBarState) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current
    val userInput = uiState.profileUserInput
    val context = LocalContext.current
    val title = stringResource(id = R.string.update_profile)
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.onNewProfileImage(uri)
            }
        }

    LaunchedEffect(key1 = true) {
        viewModel.setValues(email)
        onComposing(
            AppBarState(
                title = title,
                navigationIcon = {
                    NavigationButton(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = com.amalitech.core_ui.R.string.navigate_back)
                    ) {
                        onNavigateBack()
                    }
                }
            )
        )
    }

    LaunchedEffect(key1 = uiState) {
        val error = uiState.error
        if (error != null) {
            showSnackBar(error.asString(context))
            viewModel.clearError()
        }
        if (uiState.canNavigate) {
            onNavigateBack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(spacing.spaceMedium),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp),
            ) {
                AsyncImage(
                    model = userInput.profileImage
                        ?: userInput.profileImageUrl,
                    contentDescription = stringResource(id = R.string.profile_image),
                    placeholder = painterResource(id = com.amalitech.core_ui.R.drawable.baseline_refresh_24),
                    error = painterResource(id = com.amalitech.core_ui.R.drawable.john_doe),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
                IconButton(
                    onClick = {
                        pickMedia.launch(PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        ))
                    },
                    modifier = Modifier
                        .size(spacing.spaceLarge)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CameraAlt,
                        contentDescription = stringResource(R.string.change_your_profile_picture),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(Modifier.height(spacing.spaceMedium))
            AuthenticationTextField(
                placeholder = stringResource(id = R.string.email),
                value = userInput.email,
                onValueChange = {
                    viewModel.onNewEmail(it)
                },
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            AuthenticationTextField(
                placeholder = stringResource(id = R.string.first_name),
                value = userInput.firstName,
                onValueChange = {
                    viewModel.onNewFirstName(it)
                },
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            AuthenticationTextField(
                placeholder = stringResource(id = R.string.last_name),
                value = userInput.lastName,
                onValueChange = {
                    viewModel.onNewLastName(it)
                },
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            AuthenticationTextField(
                placeholder = stringResource(id = R.string.title),
                value = userInput.title,
                onValueChange = {
                    viewModel.onNewTitle(it)
                },
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            AuthenticationTextField(
                placeholder = stringResource(id = R.string.old_password),
                value = userInput.oldPassword,
                onValueChange = {
                    viewModel.onOldPasswordEntered(it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
                isPassword = true
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            AuthenticationTextField(
                placeholder = stringResource(id = com.amalitech.core.R.string.new_password),
                value = userInput.newPassword,
                onValueChange = {
                    viewModel.onNewPasswordEntered(it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
                isPassword = true
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            AuthenticationTextField(
                placeholder = stringResource(id = com.amalitech.core.R.string.confirm_new_password),
                value = userInput.newPasswordConfirmation,
                onValueChange = {
                    viewModel.onNewPasswordConfirmationEntered(it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Go
                ),
                onGo = {
                    viewModel.updateProfile()
                },
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
                isPassword = true
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing.spaceMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DefaultButton(
                    text = stringResource(R.string.update_profile),
                    onClick = { viewModel.updateProfile() },
                    modifier = Modifier
                        .clip(RoundedCornerShape(spacing.spaceMedium))
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(spacing.spaceExtraSmall)
                )
                DefaultButton(
                    text = stringResource(id = R.string.reset),
                    onClick = {
                        viewModel.setValues(email)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(spacing.spaceMedium))
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(spacing.spaceExtraSmall),
                    backgroundColor = MaterialTheme.colorScheme.error
                )
            }
        }
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
    }
}