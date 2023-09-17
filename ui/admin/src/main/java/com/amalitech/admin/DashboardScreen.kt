package com.amalitech.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.admin.components.DashBoardCard
import com.amalitech.admin.components.DashboardBarGraph
import com.amalitech.core_ui.CoreViewModel
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.NavigationButton
import com.amalitech.core_ui.components.PainterActionButton
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.CustomBackHandler
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(
    appState: BookMeetingRoomAppState,
    coreViewModel: CoreViewModel = koinViewModel(),
    viewModel: DashboardViewModel = koinViewModel(),
    navigateToUsersScreen: () -> Unit,
    navigateToRoomsScreen: () -> Unit,
    navigateToBookingsHistoryScreen: () -> Unit,
    onOpenDrawer: () -> Unit,
    onComposing: (AppBarState) -> Unit,
    navigateUp: () -> Unit,
    navigateToProfileScreen: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val userName by coreViewModel.userFullName
    val title = stringResource(id = com.amalitech.core_ui.R.string.dashboard)

    CustomBackHandler(appState = appState, onComposing = onComposing) {
        navigateUp()
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
                    NavigationButton {
                        onOpenDrawer()
                    }
                }
            )
        )
    }

    LaunchedEffect(key1 = uiState) {
        uiState.error?.let {
            appState.snackbarHostState.showSnackbar(it.asString(context))
            viewModel.errorShown()
        }
    }

    Box(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        Column {
            Text(text = stringResource(id = R.string.welcome_firstname, userName))
            Spacer(Modifier.height(spacing.spaceMedium))
            DashBoardCard(
                DashboardCardItem(
                    label = stringResource(R.string.users),
                    count = uiState.usersCount,
                    iconId = R.drawable.baseline_supervised_user_circle_24
                ),
                highlightColor = MaterialTheme.colorScheme.error,
                onClick = navigateToUsersScreen
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            DashBoardCard(
                DashboardCardItem(
                    label = stringResource(com.amalitech.core_ui.R.string.rooms),
                    count = uiState.roomsCount,
                    iconId = R.drawable.baseline_coffee_24
                ),
                highlightColor = MaterialTheme.colorScheme.primary,
                onClick = navigateToRoomsScreen
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            DashBoardCard(
                DashboardCardItem(
                    label = stringResource(com.amalitech.core_ui.R.string.bookings),
                    count = uiState.bookingsCount,
                    iconId = R.drawable.baseline_calendar_month_24
                ),
                highlightColor = MaterialTheme.colorScheme.tertiaryContainer,
                onClick = navigateToBookingsHistoryScreen
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            if (!uiState.loading)
                DashboardBarGraph(items = uiState.rooms)
        }
        if (uiState.loading)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}
