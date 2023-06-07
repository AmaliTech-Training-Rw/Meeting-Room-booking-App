package com.amalitech.onboarding.signup.model

data class User(
    val username: String,
    val organizationName: String,
    val email: String,
    val typeOfOrganization: String,
    val location: String,
    val password: String,
    val passwordConfirmation: String,
)
