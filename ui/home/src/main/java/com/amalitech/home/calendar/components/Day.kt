package com.amalitech.home.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.theme.LocalSpacing
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean = false,
    colors: List<Color> = emptyList(),
    onClick: (CalendarDay) -> Unit = {},
    selectedBackgroundColor: Color = MaterialTheme.colorScheme.onBackground,
    unselectedBackgroundColor: Color = MaterialTheme.colorScheme.background,
    selectedContentColor: Color = MaterialTheme.colorScheme.background,
    unselectedContentColor: Color = MaterialTheme.colorScheme.onBackground,
    enabled: Boolean = day.position == DayPosition.MonthDate
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .clip(CircleShape)
            .background(color = if (isSelected) selectedBackgroundColor else unselectedBackgroundColor)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center
    ) {
        val spacing = LocalSpacing.current
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(spacing.spaceExtraSmall),
            text = day.date.dayOfMonth.toString(),
            color = if (isSelected) {
                if (enabled) selectedContentColor else selectedContentColor.copy(alpha = 0.3f)
            } else {
                if (enabled) unselectedContentColor else unselectedContentColor.copy(alpha = 0.3f)
            },
            style = MaterialTheme.typography.bodyMedium,
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    bottom = spacing.spaceSmall,
                    start = spacing.spaceExtraSmall,
                    end = spacing.spaceExtraSmall
                ),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            for (color in colors) {
                Box(
                    modifier = Modifier
                        .height(spacing.spaceExtraSmall)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(color),
                )
            }
        }
    }
}