package com.amalitech.user.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.amalitech.core_ui.components.DefaultButton
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.UiState
import com.amalitech.ui.user.R
import com.amalitech.user.profile.components.ProfileDescriptionItem
import com.amalitech.user.profile.model.dto.UserDto
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    onUpdateProfileClick: () -> Unit,
    onToggleButtonClick: (goToAdmin: Boolean) -> Unit
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    var user: UserDto? by remember {
        mutableStateOf(null)
    }
    var isLoading: Boolean by remember {
        mutableStateOf(true)
    }
    val context = LocalContext.current
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val spacing = LocalSpacing.current
    val isAdmin: Boolean by viewModel.isAdmin
    val isUsingAdminDashboard: Boolean by viewModel.isUsingAdminDashboard

    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            is UiState.Success -> {
                user = (uiState as UiState.Success<ProfileUiState>).data?.user
                isLoading = false
            }

            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Error -> {
                (uiState as UiState.Error<ProfileUiState>).error?.let {
                    snackBarHostState.showSnackbar(
                        it.asString(context)
                    )
                }
                isLoading = false
            }

            else -> {}
        }
    }
    Box(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        if (user != null) {
            Column {
                AsyncImage(
                    model = user!!.profileImgUrl,
                    contentDescription = stringResource(id = R.string.profile_image),
                    placeholder = painterResource(id = com.amalitech.core_ui.R.drawable.baseline_refresh_24),
                    error = painterResource(id = com.amalitech.core_ui.R.drawable.baseline_broken_image_24),
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(1f)
                )
                Spacer(Modifier.height(spacing.spaceMedium))
                ProfileDescriptionItem(
                    title = stringResource(id = R.string.first_name),
                    description = user!!.firstName
                )
                Spacer(Modifier.height(spacing.spaceMedium))
                ProfileDescriptionItem(
                    title = stringResource(id = R.string.last_name),
                    description = user!!.lastName
                )
                Spacer(Modifier.height(spacing.spaceMedium))
                ProfileDescriptionItem(
                    title = stringResource(id = R.string.email),
                    description = user!!.email
                )
                Spacer(Modifier.height(spacing.spaceMedium))
                ProfileDescriptionItem(
                    title = stringResource(id = R.string.title),
                    description = user!!.title
                )
                Spacer(Modifier.height(spacing.spaceMedium))

                if (isAdmin) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.admin)
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceMedium))
                        Switch(
                            checked = isUsingAdminDashboard,
                            onCheckedChange = {
                                viewModel.updateAdminUserScreen(!isUsingAdminDashboard)
                                onToggleButtonClick(!isUsingAdminDashboard)
                            }
                        )
                    }
                }
                DefaultButton(
                    text = stringResource(id = R.string.update_profile),
                    onClick = onUpdateProfileClick
                )
            }
        }
    }
}
