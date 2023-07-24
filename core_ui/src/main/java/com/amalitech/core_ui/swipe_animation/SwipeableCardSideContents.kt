package com.amalitech.core_ui.swipe_animation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.amalitech.core_ui.swipe_animation.components.SwipeableCard
import com.amalitech.core_ui.swipe_animation.util.SwipeDirection


@Composable
fun SwipeableCardSideContents(
    isLeftContentVisible: Boolean,
    isRightContentVisible: Boolean,
    onSwipeEnd: (SwipeDirection) -> Unit,
    modifier: Modifier = Modifier,
    swipeThreshold: Float = 120f,
    leftContent: @Composable () -> Unit,
    rightContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
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
            onSwipeEnd = { direction ->
                onSwipeEnd(direction)
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
