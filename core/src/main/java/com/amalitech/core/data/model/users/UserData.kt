package com.amalitech.core.data.model.users

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserData(
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_admin")
    val isAdmin: Int,
    @Json(name = "last_login")
    val lastLogin: Any,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "location_id")
    val locationId: Int,
    @Json(name = "organisation_id")
    val organisationId: Int,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "username")
    val username: String
)