package com.amalitech.onboarding.signup

import com.amalitech.core.util.UiText
import com.amalitech.onboarding.util.Result

data class SignupUiState(
    val username: String = "",
    val organizationName: String = "",
    val email: String = "",
    val typeOfOrganization: Result<String> = Result.Loading,
    val location: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val error: UiText? = null,
    val snackBarValue: UiText? = null,
    val selectedOrganizationType: String = "",
    val finishedSigningUp: Boolean = false
)
