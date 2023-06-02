package com.amalitech.rooms.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amalitech.rooms.util.SwipeDirection

@Composable
fun SwipeableCardWithButtons(
    modifier: Modifier = Modifier,
    swipeThreshold: Float = 120f,
    leftContent: @Composable () -> Unit,
    rightContent: @Composable () -> Unit,
    cardHeight: Dp = 130.dp,
    content: @Composable () -> Unit,
) {
    var leftButtonVisible by remember { mutableStateOf(false) }
    var rightButtonVisible by remember { mutableStateOf(false) }

    val swipeDirection = remember { mutableStateOf(SwipeDirection.None) }

    Card(
        modifier = modifier
            .height(cardHeight)
            .clip(RoundedCornerShape(8.dp))
            .padding(4.dp)
            .shadow(elevation = 4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leftButtonVisible) {
                Box(
                    modifier = Modifier
                        .weight(0.2f)
                        .fillMaxHeight()
                ) {

                        leftContent()
                }
            }
            SwipeableCard(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight(),
                swipeThreshold = swipeThreshold,
                onSwipeStart = { direction -> swipeDirection.value = direction },
                onSwipeEnd = {
                    swipeDirection.value = SwipeDirection.None
                },
                content = content
            )
            if (rightButtonVisible) {
                Box(
                    modifier = Modifier
                        .weight(0.2f)
                        .fillMaxHeight()
                ) {

                        rightContent()

                }
            }
        }
    }

    when (swipeDirection.value) {
        SwipeDirection.Left -> {
            leftButtonVisible = true
            rightButtonVisible = false
        }
        SwipeDirection.Right -> {
            leftButtonVisible = false
            rightButtonVisible = true
        }
        else -> {
            leftButtonVisible = false
            rightButtonVisible = false
        }
    }
}
