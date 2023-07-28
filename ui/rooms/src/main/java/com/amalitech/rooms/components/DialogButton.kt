package com.amalitech.rooms.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.ui.rooms.R

@Composable
fun DialogButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.cancel),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    textColor: Color = MaterialTheme.colorScheme.onBackground
) {
    val spacing = LocalSpacing.current
    TextButton(
        onClick = onClick,
        modifier = modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(spacing.spaceMedium))
            .border(
                1.dp,
                textColor,
                RoundedCornerShape(spacing.spaceMedium)
            )
            .background(backgroundColor)
    ) {
        Text(
            text = text,
            color = textColor
        )
    }
}
