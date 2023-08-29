package com.amalitech.core_ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.theme.LocalSpacing

@Composable
fun DefaultButton(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    circularProgressColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    borderWidth: Dp = 0.dp,
    borderColor: Color = Color.Transparent
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier
            .height(50.dp)
            .clip(RoundedCornerShape(size = spacing.spaceExtraSmall))
            .background(color = backgroundColor)
            .clickable(enabled = enabled) {
                onClick()
            }
            .border(
                border = BorderStroke(
                    width = borderWidth,
                    color = borderColor
                )
            )
            .padding(spacing.spaceSmall),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor,
            textAlign = TextAlign.Center,
        )
        if (isLoading) {
            CircularProgressIndicator(color = circularProgressColor)
        }
    }
}

