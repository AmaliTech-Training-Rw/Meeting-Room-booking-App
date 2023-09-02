package com.amalitech.onboarding_data.remote.dto

import com.squareup.moshi.Json

data class CreateOrganizationData(
    @field:Json(name = "created_at")
    val createdAt: String,
    val email: String,
    val id: Int,
    @field:Json(name = "is_admin")
    val isAdmin: Int,
    @field:Json(name = "location_id")
    val locationId: String,
    @field:Json(name = "organisation_id")
    val organisationId: Int,
    @field:Json(name = "updated_at")
    val updatedAt: String,
    @field:Json(name = "user_id")
    val userId: Int,
    val username: String
)