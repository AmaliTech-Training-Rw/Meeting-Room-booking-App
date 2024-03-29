package com.amalitech.user.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.DefaultButton
import com.amalitech.core_ui.components.NavigationButton
import com.amalitech.core_ui.components.PainterActionButton
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.CustomBackHandler
import com.amalitech.core_ui.util.UiState
import com.amalitech.ui.user.R
import com.amalitech.user.profile.components.ProfileDescriptionItem
import com.amalitech.user.profile.model.dto.UserDto
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    appState: BookMeetingRoomAppState? = null,
    viewModel: ProfileViewModel = koinViewModel(),
    showSnackBar: (message: String) -> Unit,
    onNavigateToLogin: () -> Unit,
    navigateToProfileScreen: () -> Unit,
    onNavigateBack: () -> Unit,
    onComposing: (AppBarState) -> Unit,
    onUpdateProfileClick: (email: String) -> Unit,
    onToggleButtonClick: (goToAdmin: Boolean) -> Unit,
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    var user: UserDto? by remember {
        mutableStateOf(null)
    }
    var isLoading: Boolean by remember {
        mutableStateOf(true)
    }
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val isAdmin: Boolean by viewModel.isAdmin
    val isUsingAdminDashboard: Boolean by viewModel.isUsingAdminDashboard
    val title = stringResource(id = R.string.my_profile)
    val canNavigate by viewModel.canNavigate

    CustomBackHandler(appState = appState, onComposing = onComposing) {
        onNavigateBack()
    }

    LaunchedEffect(key1 = canNavigate) {
        if (canNavigate)
            onNavigateToLogin()
    }
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = title,
                actions = {
                    PainterActionButton {
                        navigateToProfileScreen()
                    }
                },
                navigationIcon = {
                    if (isUsingAdminDashboard) {
                        val scope = rememberCoroutineScope()
                        NavigationButton {
                            scope.launch {
                                appState?.drawerState?.open()
                            }
                        }
                    }
                }
            )
        )
        viewModel.getUser()
    }
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
                    showSnackBar(it.asString(context))
                    viewModel.onSnackBarShown()
                }
                isLoading = false
            }

            else -> {}
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(spacing.spaceMedium),
        contentAlignment = Alignment.Center
    ) {
        if (user != null) {
            Column {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = user!!.profileImgUrl,
                        contentDescription = stringResource(id = R.string.profile_image),
                        modifier = Modifier
                            .width(150.dp)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(Modifier.height(spacing.spaceMedium))
                ProfileDescriptionItem(
                    title = stringResource(id = R.string.first_name),
                    description = user!!.firstName,
                )
                Divider(
                    modifier = Modifier.padding(vertical = spacing.spaceMedium),
                    color = MaterialTheme.colorScheme.onBackground
                )
                ProfileDescriptionItem(
                    title = stringResource(id = R.string.last_name),
                    description = user!!.lastName
                )
                Divider(
                    modifier = Modifier.padding(vertical = spacing.spaceMedium),
                    color = MaterialTheme.colorScheme.onBackground
                )
                ProfileDescriptionItem(
                    title = stringResource(id = R.string.email),
                    description = user!!.email
                )
                Divider(
                    modifier = Modifier.padding(vertical = spacing.spaceMedium),
                    color = MaterialTheme.colorScheme.onBackground
                )
                ProfileDescriptionItem(
                    title = stringResource(id = R.string.title),
                    description = user!!.title
                )
                Divider(
                    modifier = Modifier.padding(vertical = spacing.spaceMedium),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        viewModel.logout()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = stringResource(R.string.logout),
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceMedium))
                    Text(
                        text = stringResource(id = R.string.logout),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                if (isAdmin) {
                    Divider(
                        modifier = Modifier.padding(vertical = spacing.spaceMedium),
                        color = MaterialTheme.colorScheme.onBackground
                    )
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
                                onToggleButtonClick(!isUsingAdminDashboard)
                                viewModel.updateAdminUserScreen(!isUsingAdminDashboard)
                            }
                        )
                    }
                }
                Divider(
                    modifier = Modifier.padding(
                        top = spacing.spaceMedium,
                        bottom = spacing.spaceExtraLarge
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                DefaultButton(
                    text = stringResource(id = R.string.update_profile),
                    onClick = { onUpdateProfileClick(user!!.email) },
                    modifier = Modifier
                        .clip(RoundedCornerShape(spacing.spaceMedium))
                        .fillMaxWidth()
                )
            }
        }
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}
