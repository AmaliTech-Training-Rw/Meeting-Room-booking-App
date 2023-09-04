package com.amalitech.onboarding_data.remote.dto

import com.squareup.moshi.Json

data class CreateOrganizationData(
    val email: String,
    val id: Int,
    @Json(name = "is_admin")
    val isAdmin: Int,
    @Json(name = "location_id")
    val locationId: String,
    @Json(name = "organisation_id")
    val organisationId: Int,
    @Json(name = "user_id")
    val userId: Int,
    val username: String
)