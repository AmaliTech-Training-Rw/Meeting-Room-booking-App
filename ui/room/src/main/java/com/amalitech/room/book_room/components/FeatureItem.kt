package com.amalitech.room.book_room.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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

@Composable
fun FeatureItem(
    feature: String,
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(spacing.spaceMedium))
            .border(
                color = borderColor,
                width = 1.dp,
                shape = RoundedCornerShape(spacing.spaceMedium)
            )
            .background(backgroundColor)
            .padding(spacing.spaceSmall),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = feature,
            style = textStyle,
            color = contentColor
        )
    }
}
