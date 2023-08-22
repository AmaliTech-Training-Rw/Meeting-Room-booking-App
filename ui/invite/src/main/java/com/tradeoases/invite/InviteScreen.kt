package com.tradeoases.invite

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.amalitech.core_ui.R
import com.amalitech.core_ui.components.DefaultButton
import com.amalitech.core_ui.components.DefaultTextField
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.theme.add_user_divider
import com.amalitech.core_ui.util.formatDate
import com.amalitech.core_ui.util.formatTime
import com.tradeoases.invite.models.Invite
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month

@Composable
fun InviteScreen(
    invitesViewModel: InvitesViewModel = koinViewModel()
) {
    val inviteState by invitesViewModel.uiState.collectAsStateWithLifecycle()

    val spacing = LocalSpacing.current

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
    ) {
        inviteState.invite.let {
            item {
                if (inviteState.invite.isEmpty()) {
                    Text(
                        text = stringResource(com.amalitech.core.R.string.no_item_found),
                    )
                }
            }
            items(items = it) { item ->
                InvitesItem(
                    item
                )
            }
        }
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
                .weight(1f)
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(LocalSpacing.current.spaceSmall)),
            model = invite.imageUrl,
            contentDescription = "item.roomName",
            placeholder = painterResource(id = R.drawable.baseline_refresh_24),
            error = painterResource(id = R.drawable.baseline_broken_image_24)
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
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Text(
                // TODO format date and add it
                text = stringResource(
                    id = com.amalitech.core.R.string.date,
                    formatDate(invite.date)
                ),
                fontSize = 12.sp,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                overflow = TextOverflow.Ellipsis
            )
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
        InviteScreen()
    }
}