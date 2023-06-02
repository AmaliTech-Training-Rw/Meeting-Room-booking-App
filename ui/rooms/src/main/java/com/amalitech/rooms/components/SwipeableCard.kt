package com.amalitech.rooms.components
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.amalitech.rooms.util.SwipeDirection

@Composable
fun SwipeableCard(
    modifier: Modifier = Modifier,
    swipeThreshold: Dp = 120.dp,
    onSwipeStart: (SwipeDirection) -> Unit,
    onSwipeEnd: () -> Unit,
    content: @Composable () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    val targetOffset = remember { mutableStateOf(0f) }
    val swipeDirection = remember { mutableStateOf(SwipeDirection.None) }

    val offset = animateFloatAsState(
        targetValue = offsetX,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = modifier
            .offset { IntOffset(offset.value.toInt(), 0) }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        swipeDirection.value = SwipeDirection.None
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        targetOffset.value = offsetX

                        if (offsetX < -swipeThreshold.toPx()) {
                            offsetX = -swipeThreshold.toPx()
                        } else if (offsetX > swipeThreshold.toPx()) {
                            offsetX = swipeThreshold.toPx()
                        }

                        if (dragAmount.x < 0) {
                            swipeDirection.value = SwipeDirection.Left
                        } else if (dragAmount.x > 0) {
                            swipeDirection.value = SwipeDirection.Right
                        }
                    },
                    onDragEnd = {
                        if (offsetX < -swipeThreshold.toPx()) {
                            targetOffset.value = -swipeThreshold.toPx()
                        } else if (offsetX > swipeThreshold.toPx()) {
                            targetOffset.value = swipeThreshold.toPx()
                        } else {
                            targetOffset.value = 0f
                        }
                        onSwipeEnd()
                    }
                )
            }
    ) {
            content()
    }

    LaunchedEffect(swipeDirection.value) {
        if (swipeDirection.value != SwipeDirection.None) {
            onSwipeStart(swipeDirection.value)
        }
    }
}
