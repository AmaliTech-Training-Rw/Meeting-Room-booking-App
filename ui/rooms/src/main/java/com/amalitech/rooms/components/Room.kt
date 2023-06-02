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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun RoomCard(
    roomName: String,
    numberOfPeople: Int,
    roomFeatures: String,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    painter: Painter,
    modifier: Modifier = Modifier
) {

    SwipeableCardWithButtons(
        modifier = modifier.fillMaxWidth(),
       rightContent = {
           SwipeAction(
               backgroundColor = Color.Red,
               icon = Icons.Filled.Delete,
               onActionClick = onDelete
           )
       },
        leftContent = {
            SwipeAction(
                backgroundColor = Color.Red,
                icon = Icons.Filled.Edit,
                onActionClick = onEdit
            )
        }
    ){
        Row {
            Image(
                painter = painter,
                contentDescription = "Room Image"
            )

            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = roomName, style = MaterialTheme.typography.labelLarge)
                Text(text = "Up to $numberOfPeople people", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = roomFeatures, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

}


@Composable
private fun SwipeAction(
    backgroundColor: Color,
    icon: ImageVector,
    onActionClick: () -> Unit,
) {
    val buttonSize = 60.dp

    Box(
        modifier = Modifier
            .size(buttonSize)

    ) {
        Surface(
            modifier = Modifier
                .size(buttonSize)
                .clip(RoundedCornerShape(8.dp))
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
}

