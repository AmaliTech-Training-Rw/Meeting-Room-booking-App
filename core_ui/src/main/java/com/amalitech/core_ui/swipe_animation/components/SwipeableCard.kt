package com.amalitech.core_ui.swipe_animation.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import com.amalitech.core_ui.swipe_animation.util.SwipeDirection
import kotlin.math.roundToInt

@Composable
fun SwipeableCard(
    modifier: Modifier = Modifier,
    swipeThreshold: Float = 120f,
    onSwipeEnd: (direction: SwipeDirection) -> Unit,
    content: @Composable () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    Box(
        modifier = modifier
            .fillMaxHeight()
            .offset{
                IntOffset(offsetX.roundToInt(), 0)
            }
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                },
                onDragStopped = {
                    if (offsetX < -swipeThreshold) {
                        onSwipeEnd(SwipeDirection.RIGHT)
                        offsetX = 0f
                    } else if (offsetX > swipeThreshold) {
                        onSwipeEnd(SwipeDirection.LEFT)
                        offsetX = 0f
                    } else {
                        offsetX = 0f
                        onSwipeEnd(SwipeDirection.NONE)
                    }
                }
            )
    ) {
        content()
    }
}