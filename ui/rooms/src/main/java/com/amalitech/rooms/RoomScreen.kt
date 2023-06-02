package com.amalitech.rooms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.rooms.components.RoomCard
import com.amalitech.ui.rooms.R

@Composable
fun RoomListScreen() {
    val spacing = LocalSpacing.current

    Scaffold(
        topBar = {

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Handle floating action button click */ },
                containerColor = Color.Yellow,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(spacing.spaceMedium),
                contentAlignment = Alignment.Center
            ) {
                RoomCard(
                    roomName = "Room 1",
                    numberOfPeople = 5,
                    roomFeatures = "Features of Room 5",
                    onDelete = {},
                    onEdit = {},
                    painter = painterResource(id = R.drawable.room_image)
                )
            }
        }
    )
}
