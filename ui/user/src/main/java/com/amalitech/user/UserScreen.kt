package com.amalitech.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.core_ui.swipe_animation.SwipeAction
import com.amalitech.core_ui.swipe_animation.SwipeableCardSideContents
import com.amalitech.core_ui.swipe_animation.util.SwipeDirection
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.ui.user.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserScreen(
    viewModel: UserViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    var isLeftContentVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var isRightContentVisible by rememberSaveable {
        mutableStateOf(false)
    }

    SwipeableCardSideContents(
        modifier = Modifier
            .fillMaxWidth()
            .height(77.dp),
        isLeftContentVisible = isLeftContentVisible,
        isRightContentVisible = isRightContentVisible,
        onSwipeEnd = { direction ->
            when (direction) {
                SwipeDirection.LEFT -> {
                    if (isRightContentVisible)
                        isRightContentVisible = false
                    else
                        isLeftContentVisible = false
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
        },
        rightContent = {
            SwipeAction(
                backgroundColor = MaterialTheme.colorScheme.error,
                icon = Icons.Filled.Delete,
                onActionClick = viewModel::onDelete,
                modifier = Modifier.padding(vertical = spacing.spaceExtraSmall)
            )
        },
        leftContent = {},
        content = {
            UserItem(isRightContentVisible)
        }
    )
}

@Composable
fun UserItem(
    isRightVisible: Boolean
) {
    val spacing = LocalSpacing.current
    val cardBg = if (isRightVisible) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.background
    }

    // TODO: is inactive an attribute coming from the server? or is it triggered by a swipe gesture?
    val activeText = if (isRightVisible) {
        R.string.inactive
    } else {
        R.string.active
    }

    val activeTextBg = if (isRightVisible) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.tertiaryContainer
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(spacing.spaceSmall))
            .background(cardBg)
            .padding(spacing.spaceSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.user),
            contentDescription = stringResource(id = R.string.user_image),
            modifier = Modifier.size(45.dp),
        )

        Spacer(Modifier.width(spacing.spaceExtraSmall))

        Column(
            modifier = Modifier
                .weight(2f)) {
            Text(
                text = stringResource(id = R.string.username),
                modifier = Modifier,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = stringResource(id = R.string.email),
                modifier = Modifier,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    textAlign = TextAlign.Center
                )
            )
        }

        // TODO: change the text and bg color when swiped
        Text(
            text = stringResource(id = activeText),
            modifier = Modifier
                .clip(RoundedCornerShape(spacing.spaceMedium))
                .padding(spacing.spaceExtraSmall)
                .clickable { },
            style = TextStyle(
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp,
                background = activeTextBg
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    BookMeetingRoomTheme {
        UserItem(true)
    }
}

@Preview(showBackground = true)
@Composable
fun UserScreenPreview() {
    BookMeetingRoomTheme {
        UserScreen()
    }
}