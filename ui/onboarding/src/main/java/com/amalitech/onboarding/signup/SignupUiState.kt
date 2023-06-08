package com.amalitech.onboarding.signup

import com.amalitech.core.util.UiText

data class SignupUiState(
    val username: String = "",
    val organizationName: String = "",
    val email: String = "",
    val typeOfOrganization: List<String> = listOf(),
    val location: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val error: UiText? = null,
    val snackBarValue: UiText? = null,
    val selectedOrganizationType: String = "",
    val finishedSigningUp: Boolean = false
)
