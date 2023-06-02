package com.amalitech.rooms.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amalitech.rooms.util.SwipeDirection

@Composable
fun SwipeableCardWithButtons(
    modifier: Modifier = Modifier,
    swipeThreshold: Dp = 120.dp,
    leftContent: @Composable () -> Unit,
    rightContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    var leftButtonVisible by remember { mutableStateOf(false) }
    var rightButtonVisible by remember { mutableStateOf(false) }

    val swipeDirection = remember { mutableStateOf(SwipeDirection.None) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leftButtonVisible) {
            Box(
                modifier = Modifier.weight(0.2f),
                contentAlignment = Alignment.Center
            ) {
                leftContent()
            }
        }
        Spacer(modifier = modifier.weight(0.1f))
        SwipeableCard(
            modifier = Modifier.weight(0.4f),
            swipeThreshold = swipeThreshold,
            onSwipeStart = { direction -> swipeDirection.value = direction },
            onSwipeEnd = {
               // swipeDirection.value = SwipeDirection.None
            },
            content = content
        )
        Spacer(modifier = modifier.weight(0.1f))
        if (rightButtonVisible) {
            Box(
                modifier = Modifier.weight(0.2f),
                contentAlignment = Alignment.Center
            ) {
                rightContent()
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


