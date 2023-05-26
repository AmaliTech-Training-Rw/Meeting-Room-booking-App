package com.amalitech.onboarding_presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amalitech.core.R
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.theme.BookMeetingRoomTheme

@Composable
fun ImageWithLegend(
    title: String?,
    description: String,
    painter: Painter,
    modifier: Modifier = Modifier,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    descriptionTextStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painter,
            contentDescription = title,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
        if (!title.isNullOrBlank()) {
            Text(
                text = title,
                style = titleTextStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(painter.intrinsicSize.width.dp)
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
        Text(
            text = description,
            style = descriptionTextStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(painter.intrinsicSize.width.dp)
        )
    }
}

@Preview
@Composable
fun Preview() {
    BookMeetingRoomTheme {
        ImageWithLegend(
            title = "This is a very long description that should take at least two lines",
            description = "This is a very long description that should take at least two lines",
            painter = painterResource(
                id =
                R.drawable.claudy
            )
        )
    }
}
