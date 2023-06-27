package com.amalitech.home.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.theme.NoRippleTheme
import com.amalitech.home.calendar.util.displayText
import com.amalitech.ui.home.R
import java.time.YearMonth

@Composable
fun SimpleCalendarTitle(
    modifier: Modifier,
    currentMonth: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
    backgroundColor: Color,
    contentColor: Color,
    buttonBackgroundColor: Color,
    buttonContentColor: Color
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .height(40.dp)
            .padding(spacing.spaceExtraSmall)
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CalendarNavigationIcon(
            icon = painterResource(id = R.drawable.baseline_chevron_left_24),
            contentDescription = stringResource(id = R.string.previous),
            onClick = goToPrevious,
            backgroundColor = buttonBackgroundColor,
            color = buttonContentColor
        )
        Spacer(Modifier.width(spacing.spaceExtraSmall))
        Text(
            modifier = Modifier
                .weight(1f),
            text = currentMonth.displayText(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = contentColor,
        )
        Spacer(Modifier.width(spacing.spaceExtraSmall))
        CalendarNavigationIcon(
            icon = painterResource(id = R.drawable.baseline_chevron_right_24),
            contentDescription = stringResource(R.string.next),
            onClick = goToNext,
            backgroundColor = buttonBackgroundColor,
            color = buttonContentColor
        )
    }
}

@Composable
private fun CalendarNavigationIcon(
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    color: Color
) {
    val spacing = LocalSpacing.current
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        IconButton(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(spacing.spaceSmall)
                )
                .background(backgroundColor)

            ,
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(LocalSpacing.current.spaceExtraSmall),
                painter = icon,
                contentDescription = contentDescription,
                tint = color
            )
        }
    }
}
