package com.amalitech.core.data.model.users

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
//data class ApiUsers(
//    @Json(name = "count")
//    val count: Int,
//    @Json(name = "data")
//    val `data`: List<UserData>
//)

@JsonClass(generateAdapter = true)
data class ApiUsers(
    @field:Json(name = "count") val count: Int,
    @field:Json(name = "data") val list: List<UserData>
)