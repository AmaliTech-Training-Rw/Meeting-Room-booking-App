package com.amalitech.admin.room

import android.net.Uri

data class AddRoomUiState(
    val name: String = "",
    val capacity: Int = 1,
    val location: String = "",
    val locationList: List<String> = listOf(),
    val imagesList: MutableList<Uri> = mutableListOf(),
    val features: String = "",
    val error: Pair<Boolean, String> = Pair(false, "")
)

// TODO: move to domain room module, after its creation
data class Room(
    val name: String,
    val capacity: Int,
    val location: String,
    val imagesList: List<Uri>,
    val features: String
)