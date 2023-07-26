package com.amalitech.booking.requests.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.amalitech.booking.model.Booking
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.formatDate1
import com.amalitech.core_ui.util.formatTime
import com.amalitech.ui.booking.R

@Composable
fun BookingRequestItem(
    booking: Booking,
    modifier: Modifier = Modifier,
    swiped: Boolean = false,
    swipedBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    unSwipedBackgroundColor: Color = MaterialTheme.colorScheme.background,
    roomNameTextColor: Color = MaterialTheme.colorScheme.inverseOnSurface,
    roomnameTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.ExtraBold
    ),
    descriptionTextColor: Color = MaterialTheme.colorScheme.onBackground,
    descriptionTextStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(spacing.spaceMedium))
            .background(
                if (swiped) swipedBackgroundColor
                else unSwipedBackgroundColor
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall)
    ) {
        AsyncImage(
            model = booking.imgUrl,
            contentDescription = booking.roomName,
            modifier = Modifier
                .clip(CircleShape)
                .aspectRatio(1f)
                .weight(0.3f),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(com.amalitech.core_ui.R.drawable.baseline_refresh_24),
            error = painterResource(id = com.amalitech.core_ui.R.drawable.baseline_broken_image_24)
        )
        Column(Modifier.weight(0.7f)) {
            Text(
                text = booking.roomName,
                color = roomNameTextColor,
                style = roomnameTextStyle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(Modifier.height(spacing.spaceExtraSmall))
            Text(
                text = stringResource(R.string.booked_by, booking.bookedBy),
                color = descriptionTextColor,
                style = descriptionTextStyle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(Modifier.height(spacing.spaceExtraSmall))
            Text(
                text = stringResource(
                    R.string.date_and_time,
                    formatDate1(booking.date),
                    formatTime(booking.startTime),
                    formatTime(booking.endTime)
                ),
                color = descriptionTextColor,
                style = descriptionTextStyle,
            )
        }
    }
}
