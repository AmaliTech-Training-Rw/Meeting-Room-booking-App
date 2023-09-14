package com.amalitech.onboarding_data.remote.dto

import com.amalitech.onboarding.signup.model.CreateOrganization

data class CreateOrganizationDto(
    val `data`: CreateOrganizationData? = null,
    val message: String? = null,
    val status: Boolean?,
) {
    fun toCreateOrganization(): CreateOrganization {
        return CreateOrganization(status ?: false)
    }
}
