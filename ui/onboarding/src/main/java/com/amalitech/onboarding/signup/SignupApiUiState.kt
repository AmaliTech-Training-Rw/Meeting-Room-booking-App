package com.amalitech.onboarding.signup

data class SignupApiUiState(
    val typeOfOrganization: List<String> = listOf(),
    val shouldNavigate: Boolean = false
)
