package com.amalitech.rooms.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.amalitech.swipe_animation.components.SwipeableCardWithButtons

@Composable
fun RoomCard(
    roomName: String,
    numberOfPeople: Int,
    roomFeatures: String,
    painter: Painter,
    modifier: Modifier = Modifier
) {
    SwipeableCardWithButtons(
        modifier = modifier.fillMaxWidth(),
        rightContent = {
            SwipeAction(
                backgroundColor = MaterialTheme.colorScheme.secondary,
                icon = Icons.Filled.Delete,
                onActionClick = {}
            )
        },
        leftContent = {
            SwipeAction(
                backgroundColor = MaterialTheme.colorScheme.primary,
                icon = Icons.Filled.Edit,
                onActionClick = {}
            )
        }
    ) {
        Row(
            modifier = modifier.fillMaxWidth().background(Color.White)
        ) {
            Image(
                painter = painter,
                contentDescription = "Room Image",
                modifier = Modifier
                    .size(120.dp)
                    .fillMaxWidth(0.4f)
                    .clip(RoundedCornerShape(16.dp))
            )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
                    .padding(vertical = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    VerticalLine(modifier=Modifier.height(40.dp))
                    Column(Modifier.padding(start = 8.dp)) {
                        Text(
                            text = roomName,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Up to $numberOfPeople people",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = roomFeatures,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Composable
fun VerticalLine(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(3.dp)
            .background(MaterialTheme.colorScheme.primary)
    )
}




@Composable
private fun SwipeAction(
    backgroundColor: Color,
    icon: ImageVector,
    onActionClick: () -> Unit,
) {

    Box(
        modifier = Modifier
            .background(backgroundColor)

    ) {
        IconButton(
            onClick = onActionClick,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )
        }

    }
}

