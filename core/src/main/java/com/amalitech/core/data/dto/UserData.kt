package com.amalitech.core.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserData(
    @Json(name = "email")
    val email: String?,
    @Json(name = "first_name")
    val firstName: String?,
    val id: Int?,
    @Json(name = "is_admin")
    val isAdmin: Int?,
    @Json(name = "last_login")
    val lastLogin: String?,
    @Json(name = "last_name")
    val lastName: String?,
    @Json(name = "locationId")
    val locationId: Int?,
    @Json(name = "organisation_id")
    val organisationId: Int?,
    @Json(name = "userId")
    val userId: Int?,
    val username: String?
)
