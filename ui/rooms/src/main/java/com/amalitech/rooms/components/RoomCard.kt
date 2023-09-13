package com.amalitech.rooms.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.amalitech.core.data.model.Room
import com.amalitech.core_ui.swipe_animation.SwipeAction
import com.amalitech.core_ui.swipe_animation.SwipeableCardSideContents
import com.amalitech.core_ui.swipe_animation.util.SwipeDirection
import com.amalitech.core_ui.theme.LocalSpacing

@Composable
fun RoomCard(
    room: Room,
    modifier: Modifier = Modifier,
    onLeftContentClick: () -> Unit,
    onRightContentClick: () -> Unit
) {
    var isLeftContentVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var isRightContentVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val spacing = LocalSpacing.current

    SwipeableCardSideContents(
        modifier = modifier
            .fillMaxWidth(),
        rightContent = {
            SwipeAction(
                backgroundColor = MaterialTheme.colorScheme.error,
                icon = Icons.Filled.Delete,
                onActionClick = onRightContentClick,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topEnd = spacing.spaceMedium,
                            bottomEnd = spacing.spaceMedium
                        )
                    )
                    .padding(vertical = spacing.spaceExtraSmall)
                    .shadow(
                        elevation = spacing.spaceExtraSmall,
                        RoundedCornerShape(
                            topEnd = spacing.spaceMedium,
                            bottomEnd = spacing.spaceMedium
                        )
                    )
            )
        },
        leftContent = {
            SwipeAction(
                backgroundColor = MaterialTheme.colorScheme.primary,
                icon = Icons.Filled.Edit,
                onActionClick = onLeftContentClick,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = spacing.spaceMedium,
                            bottomStart = spacing.spaceMedium
                        )
                    )
                    .padding(vertical = spacing.spaceExtraSmall)
                    .shadow(
                        elevation = spacing.spaceExtraSmall,
                        RoundedCornerShape(
                            topStart = spacing.spaceMedium,
                            bottomStart = spacing.spaceMedium
                        )
                    )
            )
        },
        content = {
            RoomDescription(
                room, modifier = Modifier
                    .clip(
                        if (isLeftContentVisible)
                            RoundedCornerShape(
                                topEnd = spacing.spaceMedium,
                                bottomEnd = spacing.spaceMedium
                            )
                        else if (isRightContentVisible)
                            RoundedCornerShape(
                                topStart = spacing.spaceMedium,
                                bottomStart = spacing.spaceMedium
                            )
                        else RectangleShape
                    )
                    .padding(
                        start = if (isLeftContentVisible) 0.dp else spacing.spaceExtraSmall,
                        end = if (isRightContentVisible) 0.dp else spacing.spaceExtraSmall,
                        top = spacing.spaceExtraSmall,
                        bottom = spacing.spaceExtraSmall
                    )
                    .shadow(
                        elevation = spacing.spaceExtraSmall,
                        shape = if (isLeftContentVisible)
                            RoundedCornerShape(
                                topEnd = spacing.spaceMedium,
                                bottomEnd = spacing.spaceMedium
                            )
                        else if (isRightContentVisible)
                            RoundedCornerShape(
                                topStart = spacing.spaceMedium,
                                bottomStart = spacing.spaceMedium
                            )
                        else RoundedCornerShape(spacing.spaceMedium)
                    )
                    .background(MaterialTheme.colorScheme.surface)
            )
        },
        isLeftContentVisible = isLeftContentVisible,
        isRightContentVisible = isRightContentVisible,
        onSwipeEnd = { direction ->
            when (direction) {
                SwipeDirection.LEFT -> {
                    if (isRightContentVisible)
                        isRightContentVisible = false
                    else
                        isLeftContentVisible = true
                }

                SwipeDirection.NONE -> {
                    isLeftContentVisible = false
                    isRightContentVisible = false
                }

                SwipeDirection.RIGHT -> {
                    if (isLeftContentVisible)
                        isLeftContentVisible = false
                    else
                        isRightContentVisible = true
                }
            }
        }
    )
}
