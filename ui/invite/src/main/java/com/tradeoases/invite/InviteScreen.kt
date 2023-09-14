package com.tradeoases.invite

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.amalitech.core_ui.R
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.EmptyListScreen
import com.amalitech.core_ui.components.PainterActionButton
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.formatDate
import com.amalitech.core_ui.util.formatTime
import com.tradeoases.invite.models.Invite
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month

@Composable
fun InviteScreen(
    viewModel: InvitesViewModel = koinViewModel(),
    showSnackBar: (message: String) -> Unit,
    navigateToProfileScreen: () -> Unit,
    onComposing: (AppBarState) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val invites by uiState.invite.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current
    val title = stringResource(id = R.string.invitations)
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = title,
                actions = {
                    PainterActionButton {
                        navigateToProfileScreen()
                    }
                }
            )
        )
    }

    LaunchedEffect(key1 = uiState) {
        uiState.error?.let {
            showSnackBar(it.asString(context))
            viewModel.clearError()
        }
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            items(items = invites) { item ->
                InvitesItem(
                    item
                )
            }
        }
        if (invites.isEmpty() && !uiState.loading)
            EmptyListScreen(item = stringResource(com.amalitech.ui.invite.R.string.invitations))
        if (uiState.loading)
            CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
fun InvitesItem(
    invite: Invite,
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(LocalSpacing.current.spaceSmall),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
            )
            .background(MaterialTheme.colorScheme.background)
            .padding(spacing.spaceSmall)
    ) {
        AsyncImage(
            modifier = Modifier
                .wrapContentHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(LocalSpacing.current.spaceSmall)),
            model = invite.imageUrl,
            contentDescription = invite.roomName,
            placeholder = painterResource(id = R.drawable.baseline_refresh_24),
            error = painterResource(id = R.drawable.baseline_broken_image_24),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(spacing.spaceSmall))
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(spacing.spaceSmall))
        Column(
            Modifier.weight(2f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = invite.roomName,
                fontSize = 16.sp,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            Row {
                Text(
                    // TODO format date and add it
                    text = stringResource(id = com.amalitech.core.R.string.date),
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    // TODO format date and add it
                    text = ": ${formatDate(invite.date)}",
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            Text(
                text = stringResource(
                    id = com.amalitech.core.R.string.booking_start_end_time,
                    formatTime(invite.startTime),
                    formatTime(invite.endTime)
                ),
                fontSize = 12.sp,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InvitesItemPreview() {
    BookMeetingRoomTheme {
        InvitesItem(
            Invite(
                1,
                "Zulu",
                LocalDate.of(2020, Month.JANUARY, 8),
                LocalTime.now(),
                LocalTime.MIDNIGHT,
                ""
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InviteScreenPreview() {
    BookMeetingRoomTheme {
        InviteScreen(navigateToProfileScreen = {}, showSnackBar = {}) {}
    }
}