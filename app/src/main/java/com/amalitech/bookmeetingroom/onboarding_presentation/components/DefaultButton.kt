package com.amalitech.bookmeetingroom.onboarding_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amalitech.bookmeetingroom.R
import com.amalitech.bookmeetingroom.ui.theme.BookMeetingRoomTheme
import com.amalitech.bookmeetingroom.ui.theme.LocalSpacing

@Composable
fun DefaultButton(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(size = spacing.extraSmall))
            .background(color = backgroundColor)
            .clickable {
                onClick()
            }
            .padding(spacing.small),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun defaultButtPrev() {
    BookMeetingRoomTheme {
        val painter = painterResource(id = R.drawable.claudy)
        DefaultButton(text = "Get started", onClick = { /*TODO*/ }, modifier = Modifier
            .width(painter.intrinsicSize.width.dp)
            .height(40.dp))
    }
}
