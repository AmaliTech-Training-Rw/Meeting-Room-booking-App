package com.amalitech.onboarding_data.remote.dto

import com.amalitech.onboarding.signup.model.OrganizationType
import com.amalitech.onboarding.signup.model.TypesOrganisation

data class OrganizationTypeDto(
    val typesOrganisation: List<TypesOrganisation>
) {
    fun toOrganizationType(): OrganizationType {
        return OrganizationType(typesOrganisation)
    }
}
