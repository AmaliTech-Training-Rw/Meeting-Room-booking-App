package com.amalitech.onboarding.signup

import com.amalitech.onboarding.signup.model.LocationX
import com.amalitech.onboarding.signup.model.TypesOrganisation

data class SignupUiState(
    val typeOfOrganization: List<TypesOrganisation> = listOf(),
    val locations: List<LocationX> = listOf(),
    val shouldNavigate: Boolean = false
)
