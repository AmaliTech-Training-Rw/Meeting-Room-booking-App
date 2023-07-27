package com.amalitech.rooms.book_room.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.formatLocalTime
import java.time.LocalTime

@Composable
fun TimeItem(
    time: LocalTime,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    unSelectedColor: Color = MaterialTheme.colorScheme.onBackground,
    selectedColor: Color = MaterialTheme.colorScheme.onPrimary,
    unSelectedBackgroundColor: Color = MaterialTheme.colorScheme.background,
    selectedBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    disabledColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
    disabledBackgroundColor: Color = MaterialTheme.colorScheme.background,
    enabled: Boolean = true
) {
    val spacing = LocalSpacing.current
    Row(
       modifier = modifier
           .clip(RoundedCornerShape(spacing.spaceSmall))
           .background(
               if (!enabled)
                   disabledBackgroundColor
               else {
                   if (isSelected) selectedBackgroundColor else unSelectedBackgroundColor
               }
           )
           .border(
               1.dp,
               MaterialTheme.colorScheme.onBackground,
               shape = RoundedCornerShape(spacing.spaceSmall)
           )
           .padding(spacing.spaceSmall)
           .clickable(enabled = enabled) { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val timeString = formatLocalTime(time)
        Text(
            text = timeString,
            style = textStyle,
            color = if (!enabled)
                disabledColor
            else {
                if (isSelected) selectedColor else unSelectedColor
            },
        )
    }
}
