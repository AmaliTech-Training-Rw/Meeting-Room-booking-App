package com.amalitech.onboarding_data.remote.dto

import com.amalitech.onboarding.login.model.UserProfile
import com.squareup.moshi.Json

data class LoginData(
    @field:Json(name = "created_at")
    val createdAt: String,
    val email: String,
    @field:Json(name = "first_name")
    val firstName: String?,
    val id: Int,
    @field:Json(name = "is_admin")
    val isAdmin: Int,
    @field:Json(name = "last_login")
    val lastLogin: String?,
    @field:Json(name = "last_name")
    val lastName: String?,
    @field:Json(name = "locationId")
    val locationId: Int,
    @field:Json(name = "organisation_id")
    val organisationId: Int,
    @field:Json(name = "updated_at")
    val updatedAt: String,
    @field:Json(name = "userId")
    val userId: Int,
    val username: String
) {
    fun toProfileInfo(token: String): UserProfile {
        return UserProfile(
            createdAt = createdAt,
            email = email,
            firstName = firstName?:"",
            id = id,
            isAdmin = isAdmin,
            lastLogin = lastLogin?:"",
            lastName = lastName?:"",
            locationId = locationId,
            organisationId = organisationId,
            updatedAt = updatedAt,
            userId = userId,
            username = username,
            token = token
        )
    }
}
