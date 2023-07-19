package com.amalitech.rooms.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.amalitech.core.data.model.Room
import com.amalitech.core_ui.swipe_animation.SwipeableCardSideContents
import com.amalitech.core_ui.swipe_animation.util.SwipeDirection

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
    SwipeableCardSideContents(
        modifier = modifier
            .fillMaxWidth(),
        rightContent = {
            SwipeAction(
                backgroundColor = MaterialTheme.colorScheme.error,
                icon = Icons.Filled.Delete,
                onActionClick = onRightContentClick
            )
        },
        leftContent = {
            SwipeAction(
                backgroundColor = MaterialTheme.colorScheme.inversePrimary,
                icon = Icons.Filled.Edit,
                onActionClick = onLeftContentClick
            )
        },
        content = {
            RoomDescription(
                room, modifier = Modifier
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

@Composable
private fun SwipeAction(
    backgroundColor: Color,
    icon: ImageVector,
    onActionClick: () -> Unit,
) {
    IconButton(
        onClick = onActionClick,
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)

    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.surface
        )
    }
}
