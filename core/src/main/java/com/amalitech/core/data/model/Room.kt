package com.amalitech.core.data.model

data class Room(
    val id: String,
    val roomName: String,
    val numberOfPeople: Int,
    val roomFeatures: List<String>,
    val imageUrl: List<String>,
    val locationId: Int = -1
)
