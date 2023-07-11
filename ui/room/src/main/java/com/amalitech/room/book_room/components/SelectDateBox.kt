package com.amalitech.room.book_room.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.theme.LocalSpacing

@Composable
fun SelectDateBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    shape: Shape = RoundedCornerShape(LocalSpacing.current.spaceExtraSmall)
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(spacing.spaceExtraSmall))
            .border(
                1.dp,
                MaterialTheme.colorScheme.onBackground,
                shape = shape
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.padding(spacing.spaceExtraSmall)
        )
    }
}
