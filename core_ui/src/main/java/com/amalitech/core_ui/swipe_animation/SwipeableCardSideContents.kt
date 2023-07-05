package com.amalitech.core_ui.swipe_animation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.swipe_animation.components.SwipeableCard
import com.amalitech.core_ui.swipe_animation.util.SwipeDirection


@Composable
fun SwipeableCardSideContents(
        modifier: Modifier = Modifier,
        swipeThreshold: Float = 120f,
        leftContent: @Composable () -> Unit,
        rightContent: @Composable () -> Unit,
        content: @Composable () -> Unit,
        isLeftVisible: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
        isRightVisible: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
) {
    val swipeDirection = remember { mutableStateOf(SwipeDirection.NONE) }

    Card(
            modifier = modifier
                    .clip(RoundedCornerShape(8.dp))
                    .padding(4.dp)
                    .shadow(elevation = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isLeftVisible.value) {
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
                        swipeDirection.value = SwipeDirection.NONE
                    },
                    content = content
            )
            if (isRightVisible.value) {
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
        SwipeDirection.LEFT -> {
            isLeftVisible.value = true
            isRightVisible.value = false
        }

        SwipeDirection.RIGHT -> {
            isLeftVisible.value = false
            isRightVisible.value = true
        }

        else -> {
            isLeftVisible.value = false
            isRightVisible.value = false
        }
    }

}

}

