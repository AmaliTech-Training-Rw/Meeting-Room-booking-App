package com.amalitech.core.data.data_source.remote.dto

import com.squareup.moshi.Json

data class Image(
    val id: Int?,
    @Json(name = "room_id")
    val roomId: Int?,
    val url: String?
)