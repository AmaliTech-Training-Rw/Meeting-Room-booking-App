package com.amalitech.onboarding.signup

data class SignupUiState(
    val typeOfOrganization: List<String> = listOf(),
    val shouldNavigate: Boolean = false
)
