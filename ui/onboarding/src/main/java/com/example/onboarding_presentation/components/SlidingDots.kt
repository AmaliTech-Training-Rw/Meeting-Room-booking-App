package com.example.onboarding_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.core_ui.ui.theme.BookMeetingRoomTheme

@Composable
fun SlidingDots(
    modifier: Modifier = Modifier,
    selectedIndex: Int = 1
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        (0..3).forEach {
            Dot(
                selected = selectedIndex == it,
            )
        }
    }
}

@Composable
fun Dot(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unSelectedColor: Color = MaterialTheme.colorScheme.primaryContainer,
    size: Dp = 5.dp
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
