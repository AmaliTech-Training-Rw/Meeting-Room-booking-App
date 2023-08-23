package com.amalitech.booking.requests.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.amalitech.core_ui.R
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.FeatureItem
import com.amalitech.core_ui.components.NavigationButton
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.formatTime
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookingRequestDetailScreen(
    id: String,
    viewModel: BookingRequestDetailViewModel = koinViewModel(),
    onComposing: (AppBarState) -> Unit,
    onNavigateUp: () -> Unit,
    showSnackBar: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val title = stringResource(com.amalitech.ui.booking.R.string.booking_detail)
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.getBookingDetail(bookingId = id)
        onComposing(
            AppBarState(
                title = title,
                navigationIcon = {
                    NavigationButton(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.navigate_back)
                    ) {
                        onNavigateUp()
                    }
                },
            )
        )
    }

    BackHandler {
        onNavigateUp()
    }

    LaunchedEffect(key1 = uiState) {
        val error = uiState.error
        if (error != null) {
            showSnackBar(error.asString(context))
            viewModel.clearError()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val bookingRequestDetail = uiState.bookingRequestDetail
        if (uiState.isLoading)
            CircularProgressIndicator(Modifier.align(Alignment.Center))
       if (bookingRequestDetail != null) {
           val room = bookingRequestDetail.room
           val booking = bookingRequestDetail.booking
           Column(Modifier.verticalScroll(rememberScrollState())) {
               AsyncImage(
                   model = room.imageUrl,
                   contentDescription = room.roomName,
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(200.dp)
                       /*.clip(RoundedCornerShape(spacing.spaceSmall))*/,
                   error = painterResource(id = R.drawable.larger_room),
//                   placeholder = painterResource(id = R.drawable.baseline_refresh_24),
                   contentScale = ContentScale.FillBounds
               )
               Column(modifier = Modifier.padding(spacing.spaceMedium)) {
                   Text(text = room.roomName)
                   Spacer(modifier = Modifier.height(spacing.spaceMedium))
                   DetailDescription(
                       text = stringResource(id = com.amalitech.ui.booking.R.string.booked_by),
                       description = booking.bookedBy
                   )
                   Spacer(modifier = Modifier.height(spacing.spaceMedium))
                   DetailDescription(
                       text = stringResource(id = com.amalitech.ui.booking.R.string.start_time),
                       description = formatTime(booking.startTime)
                   )
                   Spacer(modifier = Modifier.height(spacing.spaceMedium))
                   DetailDescription(
                       text = stringResource(id = com.amalitech.ui.booking.R.string.end_time),
                       description = formatTime(booking.endTime)
                   )
                   Spacer(modifier = Modifier.height(spacing.spaceLarge))
                   Text(
                       text = stringResource(com.amalitech.ui.booking.R.string.invited_people),
                       style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                   )
                   FlowRow(verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall)) {
                       booking.attendees.forEach { attendee ->
                           FeatureItem(attendee)
                           Spacer(Modifier.padding(spacing.spaceSmall))
                       }
                   }
                   Spacer(modifier = Modifier.height(spacing.spaceLarge))
                   Text(
                       text = stringResource(com.amalitech.ui.booking.R.string.note),
                       style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                   )
                   Text(
                       text = booking.note,
                       textAlign = TextAlign.Justify
                   )
               }
           }
       }
    }
}

@Composable
fun DetailDescription(
    text: String,
    description: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
    descriptionStyle: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    val spacing = LocalSpacing.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = textStyle
        )
        Spacer(modifier = Modifier.width(spacing.spaceMedium))
        Text(
            text = description,
            style = descriptionStyle
        )
    }
}
