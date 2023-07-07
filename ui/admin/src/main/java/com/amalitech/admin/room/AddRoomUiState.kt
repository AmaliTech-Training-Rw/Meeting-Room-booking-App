package com.amalitech.admin.room

data class AddRoomUiState(
    val name: String = "",
    val capacity: Int = 1,
    val location: String = "",
    val locationList: List<String> = listOf(),
    val features: String = "",
    val error: Pair<Boolean, String> = Pair(false, "")
)