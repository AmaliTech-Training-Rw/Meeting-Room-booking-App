package com.amalitech.admin.room

import android.net.Uri
import com.amalitech.core.util.UiText

data class AddRoomUiState(
    val name: String = "",
    val capacity: Int = 1,
    val location: String = "",
    val locationList: List<String> = listOf(),
    val imagesList: List<Uri> = emptyList(),
    val features: String = "",
    val error: Pair<Boolean, String> = Pair(false, ""),
    val snackBar: UiText? = null,
    val canNavigate: Boolean = false
)

// TODO: move to domain room module, after its creation
data class Room(
    val name: String,
    val capacity: Int,
    val location: String,
    val imagesList: List<Uri>,
    val features: String
)