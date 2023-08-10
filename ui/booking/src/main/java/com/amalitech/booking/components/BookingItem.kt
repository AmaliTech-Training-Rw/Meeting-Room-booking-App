package com.amalitech.booking.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.amalitech.booking.model.Booking
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.formatDate
import com.amalitech.core_ui.util.formatTime
import com.amalitech.ui.booking.R

@Composable
fun BookingItem(
    item: Booking,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    nameTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    descriptionTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    dividerColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = RoundedCornerShape(LocalSpacing.current.spaceSmall),
    borderColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                shape = shape,
                color = borderColor
            )
            .background(backgroundColor)
            .padding(spacing.spaceSmall)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(shape),
            model = item.imgUrl,
            contentDescription = item.roomName,
            placeholder = painterResource(id = com.amalitech.core_ui.R.drawable.baseline_refresh_24),
            error = painterResource(id = com.amalitech.core_ui.R.drawable.room)
        )
        Spacer(modifier = Modifier.width(spacing.spaceSmall))
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp),
            color = dividerColor
        )
        Spacer(modifier = Modifier.width(spacing.spaceSmall))
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = item.roomName,
                style = nameTextStyle,
                color = contentColor,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Text(
                // TODO format date and add it
                text = stringResource(id = R.string.booking_date, formatDate(item.date)),
                style = descriptionTextStyle,
                color = contentColor,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            Text(
                text = stringResource(
                    id = R.string.booking_start_end_time,
                    formatTime(item.startTime),
                    formatTime(item.endTime)
                ),
                style = descriptionTextStyle,
                color = contentColor,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun Test() {
    Text("abcd")
}
