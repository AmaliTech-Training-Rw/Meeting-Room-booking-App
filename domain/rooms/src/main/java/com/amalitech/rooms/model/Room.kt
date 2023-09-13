package com.amalitech.rooms.model

import android.net.Uri

data class Room(
    val id: Int = -1,
    val name: String,
    val capacity: Int,
    val location: Int,
    val imagesList: List<Uri>,
    val features: List<String>
)
