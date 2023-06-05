package com.amalitech.swipe_animation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.unit.dp
import com.amalitech.swipe_animation.components.SwipeableCard
import com.amalitech.swipe_animation.util.SwipeDirection

@Composable
fun SwipeableCardSideContents(
    modifier: Modifier = Modifier,
    swipeThreshold: Float = 120f,
    leftContent: @Composable () -> Unit,
    rightContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    var isLeftContentVisible by remember { mutableStateOf(false) }
    var isRightContentVisible by remember { mutableStateOf(false) }
    val swipeDirection = remember { mutableStateOf(SwipeDirection.None) }

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .padding(4.dp)
            .shadow(elevation = 4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLeftContentVisible) {
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
            if (isRightContentVisible) {
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
            isLeftContentVisible = true
            isRightContentVisible = false
        }
        SwipeDirection.Right -> {
            isLeftContentVisible = false
            isRightContentVisible = true
        }
        else -> {
            isLeftContentVisible = false
            isRightContentVisible = false
        }
    }
}
