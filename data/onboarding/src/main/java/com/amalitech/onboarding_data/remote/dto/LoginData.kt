package com.amalitech.onboarding_data.remote.dto

import com.amalitech.onboarding.login.model.UserProfile
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginData(
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
) {
    fun toProfileInfo(token: String): UserProfile {
        return UserProfile(
            email = email?:"",
            firstName = firstName?:"",
            id = id?:-1,
            isAdmin = isAdmin?:0,
            lastName = lastName?:"",
            locationId = locationId?:-1,
            organisationId = organisationId?:-1,
            userId = userId?:-1,
            username = username?:"",
            token = token
        )
    }
}