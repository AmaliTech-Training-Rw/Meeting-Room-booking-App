package com.amalitech.core.data.model.users

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiUsers(
    @Json(name = "count")
    val count: Int,
    @Json(name = "data")
    val `data`: List<UserData>
)