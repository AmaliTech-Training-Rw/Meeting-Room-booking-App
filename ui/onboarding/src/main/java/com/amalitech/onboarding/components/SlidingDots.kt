package com.amalitech.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.onboarding.maxScreens

@Composable
fun SlidingDots(
    modifier: Modifier = Modifier,
    selectedIndex: Int = 1
) {
    val spacing = LocalSpacing.current
    Row(modifier = modifier) {
        (0 until maxScreens).forEach {
            Dot(
                selected = selectedIndex == it
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
        }
    }
}

@Composable
fun Dot(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unSelectedColor: Color = MaterialTheme.colorScheme.outline,
    size: Dp = 10.dp
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(size)
            .background(
                color = if (selected) selectedColor else unSelectedColor,
                shape = CircleShape
            )
    )
}

@Preview
@Composable
fun DotPrev() {
    BookMeetingRoomTheme {
        Dot(selected = false)
    }
}

@Preview
@Composable
fun SlidingDotsPrev() {
    BookMeetingRoomTheme {
        SlidingDots(modifier = Modifier.width(100.dp))
    }
}
