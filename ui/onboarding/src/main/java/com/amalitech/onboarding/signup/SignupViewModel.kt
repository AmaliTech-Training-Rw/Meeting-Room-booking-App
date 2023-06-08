package com.amalitech.onboarding.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.core.util.UiText
import com.amalitech.onboarding.signup.model.User
import com.amalitech.onboarding.signup.use_case.SignupUseCase
import com.amalitech.core.R
import com.amalitech.onboarding.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel(
    private val signupUseCase: SignupUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchOrganizations()
    }

    /**
     * onNewEmail - trims and adds value of email entered by the user in our state
     *
     * @param newEmail the email address entered by the user
     */
    fun onNewEmail(newEmail: String) {
        _uiState.update { loginUiState ->
            loginUiState.copy(
                email = newEmail.trim()
            )
        }
    }

    /**
     * onNewPassword - trims and adds value of password entered by the user in our state
     *
     * @param newPassword the password entered by the user
     */
    fun onNewPassword(newPassword: String) {
        _uiState.update { loginUiState ->
            loginUiState.copy(
                password = newPassword.trim()
            )
        }
    }

    /**
     * onNewLocation - trims and adds value of location entered by the user in our state
     *
     * @param location the location entered by the user
     */
    fun onNewLocation(location: String) {
        _uiState.update { loginUiState ->
            loginUiState.copy(
                location = location.trim()
            )
        }
    }

    /**
     * onNewOrganizationName - trims and adds value of organization name
     * entered by the user in our state
     *
     * @param organizationName the location entered by the user
     */
    fun onNewOrganizationName(organizationName: String) {
        _uiState.update { loginUiState ->
            loginUiState.copy(
                organizationName = organizationName.trim()
            )
        }
    }

    /**
     * onNewUserName - trims and adds value of username
     * entered by the user in our state
     *
     * @param username the location entered by the user
     */
    fun onNewUsername(username: String) {
        _uiState.update { loginUiState ->
            loginUiState.copy(
                username = username.trim()
            )
        }
    }

    /**
     * onNewPasswordConfirmation - trims and adds value of password confirmation entered by the user in our state
     * then checks if the two passwords match
     *
     * @param password the password confirmation entered by the user
     */

    fun onNewPasswordConfirmation(password: String) {
        _uiState.update { resetPasswordUiState ->
            resetPasswordUiState.copy(
                passwordConfirmation = password.trim(),
                error = signupUseCase.checkPasswordsMatch(
                    _uiState.value.password,
                    _uiState.value.passwordConfirmation
                )
            )
        }
    }

    fun onSelectedOrganizationType(type: String) {
        _uiState.update { signupUiState ->
            signupUiState.copy(
                selectedOrganizationType = type
            )
        }
    }

    fun fetchOrganizations() {
        _uiState.update { signupUiState ->
            signupUiState.copy(
                typeOfOrganization = Result.Loading
            )
        }
        viewModelScope.launch {
            val result = signupUseCase.fetchOrganizationsType()
            _uiState.update { signupUiState ->
                signupUiState.copy(
                    typeOfOrganization = result
                )
            }
        }
    }

    fun onSignupClick() {
        validateData()
        if (_uiState.value.error == null) {
            val result = signupUseCase.signup(
                user = User(
                    _uiState.value.username,
                    _uiState.value.organizationName,
                    _uiState.value.email,
                    _uiState.value.selectedOrganizationType,
                    _uiState.value.location,
                    _uiState.value.password,
                    _uiState.value.passwordConfirmation,
                )
            )
            if (result == null) {
                _uiState.update { signupUiState ->
                    signupUiState.copy(
                        snackBarValue = UiText.StringResource(R.string.your_account_is_created),
                        finishedSigningUp = true
                    )
                }
            } else {
                _uiState.update { signupUiState ->
                    signupUiState.copy(
                        error = result
                    )
                }
            }
        }
    }

    private fun validateData() {
        val emailValidationResult = signupUseCase.validateEmail(_uiState.value.email)
        val passwordValidationResult = signupUseCase.validatePassword(_uiState.value.password)
        val valuesNotBlankResult = signupUseCase.checkValuesNotBlank(
            _uiState.value.email,
            _uiState.value.password,
            _uiState.value.passwordConfirmation,
            _uiState.value.location,
            _uiState.value.username,
            _uiState.value.selectedOrganizationType
        )
        val passwordsMatchResult = signupUseCase.checkPasswordsMatch(
            _uiState.value.password,
            _uiState.value.passwordConfirmation
        )

        when {
            emailValidationResult != null -> updateStateWithError(emailValidationResult)
            passwordValidationResult != null -> updateStateWithError(passwordValidationResult)
            valuesNotBlankResult != null -> updateStateWithError(valuesNotBlankResult)
            passwordsMatchResult != null -> updateStateWithError(passwordsMatchResult)
            _uiState.value.selectedOrganizationType.isBlank() -> updateStateWithError(
                UiText.StringResource(R.string.error_no_organization_type_selected)
            )
            else -> {
                val isEmailAvailable = signupUseCase.isEmailAvailable(_uiState.value.email)
                val isUsernameAvailable = signupUseCase.isUsernameAvailable(_uiState.value.username)

                if (!isEmailAvailable) {
                    updateStateWithError(UiText.StringResource(R.string.error_email_address_already_taken))
                    return
                }
                if (!isUsernameAvailable) {
                    updateStateWithError(UiText.StringResource(R.string.error_username_address_already_taken))
                    return
                }
            }
        }
    }

    private fun updateStateWithError(error: UiText) {
        _uiState.update { signupUiState ->
            signupUiState.copy(
                error = error
            )
        }
    }

    /**
     * onSnackBarShown - Reset the snackbarvalue in our state to null
     */
    fun onSnackBarShown() {
        _uiState.update { loginUiState ->
            loginUiState.copy(
                snackBarValue = null
            )
        }
    }

    fun validateArguments(
        organizationName: String?,
        typeOfOrganization: String?,
        location: String?,
        email: String?
    ) {
        if (organizationName.isNullOrBlank() || email.isNullOrBlank() || location.isNullOrBlank() || typeOfOrganization.isNullOrBlank()) {
            _uiState.update { signupUiState ->
                signupUiState.copy(
                    error = UiText.StringResource(R.string.error_the_link_doesnt_work)
                )
            }
        } else {
            onNewLocation(location)
            onNewEmail(email)
            onNewOrganizationName(organizationName)
            onSelectedOrganizationType(typeOfOrganization)
        }
    }
}
