package com.amalitech.rooms.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amalitech.core.data.model.Room
import com.amalitech.core_ui.swipe_animation.SwipeableCardSideContents
import com.amalitech.core_ui.theme.LocalSpacing

@Composable
fun RoomCard(
    room: Room,
    modifier: Modifier = Modifier
) {
    SwipeableCardSideContents(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        rightContent = {
            SwipeAction(
                backgroundColor = MaterialTheme.colorScheme.error,
                icon = Icons.Filled.Delete,
                onActionClick = {}
            )
        },
        leftContent = {
            SwipeAction(
                backgroundColor = MaterialTheme.colorScheme.inversePrimary,
                icon = Icons.Filled.Edit,
                onActionClick = {}
            )
        },
        content = {
            RoomDescription(
                room, modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            )
        }
    )
}

@Composable
fun RoomDescription(
    room: Room,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(room.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .fillMaxWidth(0.4f)
                .clip(RoundedCornerShape(spacing.spaceMedium))

        )
        Spacer(modifier = Modifier.width(spacing.spaceMedium))
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
                .padding(vertical =spacing.spaceMedium)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                VerticalLine(modifier = Modifier.height(spacing.spaceLarge))
                Column(Modifier.padding(start = spacing.spaceSmall)) {
                    Text(
                        text = room.roomName,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Up to ${room.numberOfPeople} people",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            Text(
                text = room.roomFeatures,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
fun VerticalLine(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(LocalSpacing.current.spaceExtraSmall)
            .background(MaterialTheme.colorScheme.inversePrimary)
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

