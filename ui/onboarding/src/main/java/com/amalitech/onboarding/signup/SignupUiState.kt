package com.amalitech.onboarding.signup

data class SignupUiState(
    val username: String = "",
    val organizationName: String = "",
    val email: String = "",
    val location: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val selectedOrganizationType: String = "",
)
