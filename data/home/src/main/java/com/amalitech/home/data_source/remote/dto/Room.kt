package com.amalitech.home.data_source.remote.dto

import com.squareup.moshi.Json

data class Room(
    val capacity: String?,
    val id: Int?,
    @Json(name = "location_id")
    val locationId: Int?,
    val name: String?,
    @Json(name = "organisation_id")
    val organisationId: Int?,
    @Json(name = "user_id")
    val userId: Int?
)