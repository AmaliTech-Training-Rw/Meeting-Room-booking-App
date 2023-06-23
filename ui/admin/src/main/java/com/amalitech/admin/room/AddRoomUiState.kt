package com.amalitech.admin.room

data class AddRoomUiState(
    val name: String = "",
    val capacity: Int = 1,
    val location: String = "",
    val features: String = "",
)