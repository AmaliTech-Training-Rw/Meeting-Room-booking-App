package com.amalitech.user.data_source.remote

import com.squareup.moshi.Json
import com.amalitech.user.models.User

data class UsersData(
    val active: Int?,
    val email: String?,
    @Json(name = "first_name")
    val firstName: String?,
    val id: Int?,
    @Json(name = "is_admin")
    val isAdmin: Int?,
    @Json(name = "last_name")
    val lastName: String?,
    @Json(name = "location_id")
    val locationId: Int?,
    @Json(name = "organisation_id")
    val organisationId: Int?,
    val url: String?,
    @Json(name = "user_id")
    val userId: Int?,
    val username: String?
) {
    fun toUser(): User {
        return User(
            userId = id.toString(),
            profilePic = url ?: "",
            username = username ?: "",
            email = email ?: "",
            isActive = active == 1
        )
    }
}
