package com.amalitech.booking.requests.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amalitech.booking.model.Booking
import com.amalitech.core_ui.swipe_animation.SwipeableCardSideContents
import com.amalitech.core_ui.swipe_animation.util.SwipeDirection
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.ui.booking.R

@Composable
fun BookingRequestCard(
    booking: Booking,
    modifier: Modifier = Modifier,
    onRightContentClick: () -> Unit,
    onLeftContentClick: () -> Unit
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
            .fillMaxSize(),
        rightContent = {
            TextButton(
                onClick = onRightContentClick,
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.error)
            ) {
                Text(
                    text = stringResource(R.string.decline),
                    color = MaterialTheme.colorScheme.onError
                )
            }
        },
        leftContent = {
            TextButton(
                onClick = onLeftContentClick,
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(R.string.approve),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        content = {
            BookingRequestItem(
                booking, modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(spacing.spaceMedium))
                    .padding(
                        start = if (isLeftContentVisible) 0.dp else spacing.spaceExtraSmall,
                        end = if (isRightContentVisible) 0.dp else spacing.spaceExtraSmall,
                        top = spacing.spaceExtraSmall,
                        bottom = spacing.spaceExtraSmall
                    )
//                    .shadow(
//                        elevation = spacing.spaceExtraSmall,
//                        shape = RoundedCornerShape(spacing.spaceMedium)
//                    )
                    .background(
                        if (isLeftContentVisible || isRightContentVisible)
                            MaterialTheme.colorScheme.outline
                        else MaterialTheme.colorScheme.background
                    )
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
