package com.example.bookings.data_source.remote.dto

import com.squareup.moshi.Json

data class ImageDto(
    @Json(name = "room_id")
    val roomId: Int?,
    val url: String?
)