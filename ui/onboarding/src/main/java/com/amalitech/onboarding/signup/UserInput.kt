package com.amalitech.onboarding.signup

data class UserInput(
    val username: String = "",
    val organizationName: String = "",
    val email: String = "",
    val location: Int = -1,
    val password: String = "",
    val passwordConfirmation: String = "",
    val selectedOrganizationType: Int = -1,
    val token: String = ""
)
