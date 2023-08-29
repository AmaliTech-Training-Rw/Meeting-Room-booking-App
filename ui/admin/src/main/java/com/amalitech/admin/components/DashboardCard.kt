package com.amalitech.admin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amalitech.admin.DashboardCardItem
import com.amalitech.core_ui.theme.LocalSpacing

@Composable
fun DashBoardCard(
    cardItem: DashboardCardItem,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    highlightColor: Color = MaterialTheme.colorScheme.primary,
    highlightHeight: Dp = LocalSpacing.current.spaceSmall,
    labelTextStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    countTextStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
    iconSize: Dp = LocalSpacing.current.spaceLarge,
    cardHeight: Dp = 100.dp,
    onClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(cardHeight)
            .background(backgroundColor)
            .padding(1.dp)
            .shadow(1.dp, shape = RoundedCornerShape(2.dp))
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text(
                    text = cardItem.label,
                    style = labelTextStyle,
                    color = contentColor
                )
                Spacer(Modifier.height(spacing.spaceSmall))
                Text(
                    text = cardItem.count.toString(),
                    style = countTextStyle,
                    color = contentColor
                )
            }

            Icon(
                painter = painterResource(cardItem.iconId),
                contentDescription = cardItem.label,
                modifier = Modifier.size(iconSize)
            )
        }
        Spacer(modifier = Modifier
            .align(Alignment.BottomStart)
            .fillMaxWidth()
            .padding(2.dp)
            .height(highlightHeight)
            .background(highlightColor)
        )
    }
}
