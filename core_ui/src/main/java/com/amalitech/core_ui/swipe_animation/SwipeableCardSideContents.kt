package com.amalitech.core_ui.swipe_animation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import com.amalitech.core_ui.swipe_animation.components.SwipeableCard
import com.amalitech.core_ui.swipe_animation.util.SwipeDirection
import com.amalitech.core_ui.theme.LocalSpacing


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
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(spacing.spaceSmall))
            .padding(spacing.spaceExtraSmall)
            .shadow(elevation = spacing.spaceExtraSmall),
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
