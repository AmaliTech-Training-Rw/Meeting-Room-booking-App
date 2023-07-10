package com.amalitech.home.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.home.calendar.BookingUiState
import com.amalitech.core_ui.util.formatTime
import com.amalitech.ui.home.R

@Composable
fun BookingItem(
    item: BookingUiState,
    modifier: Modifier = Modifier,
    contentColor: Color,
    backgroundColor: Color
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(spacing.spaceSmall))
            .background(backgroundColor)
            .padding(spacing.spaceSmall),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(
                id = R.string.booking_time,
                formatTime(item.startTime),
                formatTime(item.endTime)
            ),
            color = contentColor
        )
        Text(
            text = item.roomName,
            color = contentColor
        )
    }
}
