package com.amalitech.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.amalitech.admin.components.DashBoardCard
import com.amalitech.admin.components.DashboardBarGraph
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.NavigationButton
import com.amalitech.core_ui.components.PainterActionButton
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.CustomBackHandler
import kotlin.random.Random

@Composable
fun DashboardScreen(
    appState: BookMeetingRoomAppState,
    navigateToUsersScreen: () -> Unit,
    navigateToRoomsScreen: () -> Unit,
    navigateToBookingsHistoryScreen: () -> Unit,
    onOpenDrawer: () -> Unit,
    onComposing: (AppBarState) -> Unit,
    navigateUp: () -> Unit,
    navigateToProfileScreen: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val graphItems = (1..20).map {
        RoomsBookedTime(
            roomId = it,
            bookedTime = Random.nextInt(40, 130).toFloat(),
            roomName = "room $it"
        )
    }
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

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxSize()
        .padding(spacing.spaceMedium)
    ) {
        Column() {
            Text(text = stringResource(id = R.string.welcome_firstname, "John Doe"))
            Spacer(Modifier.height(spacing.spaceMedium))
            DashBoardCard(
                DashboardCardItem(
                    label = stringResource(R.string.users),
                    count = 73,
                    iconId = R.drawable.baseline_supervised_user_circle_24
                ),
                highlightColor = MaterialTheme.colorScheme.error,
                onClick = navigateToUsersScreen
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            DashBoardCard(
                DashboardCardItem(
                    label = stringResource(com.amalitech.core_ui.R.string.rooms),
                    count = 12,
                    iconId = R.drawable.baseline_coffee_24
                ),
                highlightColor = MaterialTheme.colorScheme.primary,
                onClick = navigateToRoomsScreen
            )
            Spacer(Modifier.height(spacing.spaceMedium))
            DashBoardCard(
                DashboardCardItem(
                    label = stringResource(com.amalitech.core_ui.R.string.bookings),
                    count = 73,
                    iconId = R.drawable.baseline_calendar_month_24
                ),
                highlightColor = MaterialTheme.colorScheme.tertiaryContainer,
                onClick = navigateToBookingsHistoryScreen
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        DashboardBarGraph(items = graphItems)
    }
}
