package com.amalitech.swipe_animation.components
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.amalitech.swipe_animation.util.SwipeDirection

@Composable
fun SwipeableCard(
    modifier: Modifier = Modifier,
    swipeThreshold: Float = 120f,
    onSwipeStart: (SwipeDirection) -> Unit,
    onSwipeEnd: () -> Unit,
    content: @Composable () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    val swipeDirection = remember { mutableStateOf(SwipeDirection.None) }
    Box(
        modifier = modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                },
                onDragStopped = {

                    if (offsetX < -swipeThreshold) {
                        swipeDirection.value = SwipeDirection.Right
                    } else if (offsetX > swipeThreshold) {
                        swipeDirection.value = SwipeDirection.Left
                    } else {
                        onSwipeEnd()
                    }

                }
            )
    ) {
        content()
    }
    LaunchedEffect(swipeDirection.value) {
        if (swipeDirection.value != SwipeDirection.None) {
            onSwipeStart(swipeDirection.value)
        }
    }
}
