package com.amalitech.onboarding.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.core.R
import com.amalitech.core.util.UiText
import com.amalitech.onboarding.signup.model.User
import com.amalitech.onboarding.signup.use_case.SignupUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel(
    private val signupUseCase: SignupUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState = _uiState.asStateFlow()

    var job: Job? = null
        private set

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
                    password.trim()
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

        viewModelScope.launch {
            val result = signupUseCase.fetchOrganizationsType()
            if (result.data != null) {
                _uiState.update { signupUiState ->
                    signupUiState.copy(
                        typeOfOrganization = result.data!!
                    )
                }
            } else if (result.error != null) {
                _uiState.update { signupUiState ->
                    signupUiState.copy(
                        error = result.error!!
                    )
                }
            } else {
                _uiState.update { signupUiState ->
                    signupUiState.copy(
                        error = UiText.StringResource(R.string.error_default_message)
                    )
                }
            }
        }
    }

    /**
     * onSignupClick - Create an account for the user if there
     * is no error after validation. If there is an error triggered during the signup process,
     * this error is added to the state so that it can be displayed on the screen
     */
    fun onSignupClick() {
        if (job?.isActive == true)
            return
        job = viewModelScope.launch {
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
    }

    /**
     * validateData - Checks if values received by the viewModel are correct
     * and can be used to sign to create an account for the user. If there is an error
     * it updates the viewModel state with it.
     */
    private fun validateData() {
        updateStateWithError(null)
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

    /**
     * updateStateWithError - adds error to the state
     * @param error The error to be added
     */
    private fun updateStateWithError(error: UiText?) {
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

    /**
     * submitValues - Submits values received by the nav back stack entry to the viewModel
     * @param email Value of email address received by the nav back stack entry
     * @param organizationName Value received by the nav back stack entry
     * @param location Value received by the nav back stack entry
     * @param typeOfOrganization Value received by the nav back stack entry
     */
    fun submitValues(
        organizationName: String,
        typeOfOrganization: String,
        location: String,
        email: String
    ) {
        onNewLocation(location)
        onNewEmail(email)
        onNewOrganizationName(organizationName)
        onSelectedOrganizationType(typeOfOrganization)
    }

    /**
     * isInvitedUser - Checks if there are all required arguments for invited users.
     * If any of the arguments is null or blank, then it's not an invited user, because
     * the navHost can only receive all the arguments together or none of them.
     * @param email Value of email address received by the nav back stack entry
     * @param organizationName Value received by the nav back stack entry
     * @param location Value received by the nav back stack entry
     * @param typeOfOrganization Value received by the nav back stack entry
     * @return false if any of the params is null or blank, true otherwise
     */
    fun isInvitedUser(
        email: String?,
        organizationName: String?,
        location: String?,
        typeOfOrganization: String?
    ): Boolean {
        return !email.isNullOrBlank() && !organizationName.isNullOrBlank() && !location.isNullOrBlank() && !typeOfOrganization.isNullOrBlank()
    }
}
