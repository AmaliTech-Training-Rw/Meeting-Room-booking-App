package com.amalitech.onboarding.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.amalitech.core.R
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.core_ui.util.UiState
import com.amalitech.onboarding.signup.model.User
import com.amalitech.onboarding.signup.use_case.SignupUseCasesWrapper
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel(
    private val signupUseCasesWrapper: SignupUseCasesWrapper
) : BaseViewModel<SignupUiState>() {

    private val _userInput = mutableStateOf(UserInput())
    val userInput: State<UserInput>
        get() = _userInput


    init {
        fetchOrganizationsAndLocations()
    }

    /**
     * onNewEmail - trims and adds value of email entered by the user in our state
     *
     * @param newEmail the email address entered by the user
     */
    fun onNewEmail(newEmail: String) {
        _userInput.value = _userInput.value.copy(
            email = newEmail.trim()
        )
    }

    /**
     * onNewToken - trims and adds value of token entered by the user in our state
     *
     * @param newToken the email address entered by the user
     */
    private fun onNewToken(newToken: String) {
        _userInput.value = _userInput.value.copy(
            token = newToken.trim()
        )
    }

    /**
     * onNewPassword - trims and adds value of password entered by the user in our state
     *
     * @param newPassword the password entered by the user
     */
    fun onNewPassword(newPassword: String) {
        _userInput.value = _userInput.value.copy(
            password = newPassword.trim()
        )
    }

    /**
     * onNewLocation - trims and adds value of location entered by the user in our state
     *
     * @param locationId the location entered by the user
     */
    fun onLocationSelected(locationId: Int) {
        _userInput.value = _userInput.value.copy(
            location = locationId
        )
    }

    /**
     * onNewOrganizationName - trims and adds value of organization name
     * entered by the user in our state
     *
     * @param organizationName the location entered by the user
     */
    fun onNewOrganizationName(organizationName: String) {
        _userInput.value = _userInput.value.copy(
            organizationName = organizationName
        )
    }

    /**
     * onNewUserName - trims and adds value of username
     * entered by the user in our state
     *
     * @param username the location entered by the user
     */
    fun onNewUsername(username: String) {
        _userInput.value = _userInput.value.copy(
            username = username
        )
    }

    /**
     * onNewPasswordConfirmation - trims and adds value of password confirmation entered by the user in our state
     * then checks if the two passwords match
     *
     * @param password the password confirmation entered by the user
     */

    fun onNewPasswordConfirmation(password: String) {
        _userInput.value = _userInput.value.copy(
            passwordConfirmation = password
        )
    }

    fun onSelectedOrganizationType(type: Int) {
        _userInput.value = _userInput.value.copy(
            selectedOrganizationType = type
        )
    }

    private fun fetchOrganizationsAndLocations() {
        if (job?.isActive == true)
            return

        job = viewModelScope.launch {
            _uiStateFlow.update {
                UiState.Loading()
            }
            val resultOrganization = signupUseCasesWrapper.fetchOrganizationsTypeUseCase()
            val resultLocation = signupUseCasesWrapper.fetchLocationsUseCase()
            if (resultOrganization.data != null && resultLocation.data != null) {
                _uiStateFlow.update {
                    UiState.Success(
                        SignupUiState(
                            resultOrganization.data!!,
                            resultLocation.data!!
                        )
                    )
                }
            } else if (resultOrganization.error != null || resultLocation.error != null) {
                if (resultOrganization.error != null)
                    _uiStateFlow.update {
                        UiState.Error(
                            resultOrganization.error!!
                        )
                    }
                if (resultLocation.error != null)
                    _uiStateFlow.update {
                        UiState.Error(
                            resultLocation.error!!
                        )
                    }
            } else {
                _uiStateFlow.update {
                    UiState.Error(
                        UiText.StringResource(R.string.error_default_message)
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
            _uiStateFlow.update {
                UiState.Loading()
            }
            validateData()
            if (_uiStateFlow.value !is UiState.Error) {
                val result =
                    if (_userInput.value.token.isNotBlank())
                        signupUseCasesWrapper.createUserUseCase(
                            token = _userInput.value.token,
                            username = _userInput.value.username,
                            password = _userInput.value.password,
                            passwordConfirmation = _userInput.value.passwordConfirmation
                        )
                    else
                        signupUseCasesWrapper.signupUseCase(
                            user = User(
                                _userInput.value.username,
                                organizationName = _userInput.value.organizationName,
                                email = _userInput.value.email,
                                typeOfOrganization = _userInput.value.selectedOrganizationType,
                                location = _userInput.value.location,
                                password = _userInput.value.password,
                                passwordConfirmation = _userInput.value.passwordConfirmation,
                            )
                        )
                if (result == null) {
                    _uiStateFlow.update {
                        UiState.Success(SignupUiState(shouldNavigate = true))
                    }
                } else {
                    _uiStateFlow.update {
                        UiState.Error(result)
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
        if (_userInput.value.token.isNotBlank()) {
            val passwordMatch = signupUseCasesWrapper.checkPasswordsMatchUseCase(
                _userInput.value.password,
                _userInput.value.passwordConfirmation
            )
            val emailValid = signupUseCasesWrapper.validateEmailUseCase(_userInput.value.email)
            val passwordValid =
                signupUseCasesWrapper.checkValuesNotBlankUseCase(_userInput.value.password)
            when {
                passwordMatch != null -> updateStateWithError(passwordMatch)
                passwordValid != null -> updateStateWithError(passwordValid)
                emailValid != null -> updateStateWithError(emailValid)
            }
            return
        }
        val passwordValidationResult =
            signupUseCasesWrapper.validatePasswordUseCase(_userInput.value.password)
        val emailValidationResult =
            signupUseCasesWrapper.validateEmailUseCase(_userInput.value.email)
        val valuesNotBlankResult = signupUseCasesWrapper.checkValuesNotBlankUseCase(
            _userInput.value.email,
            _userInput.value.password,
            _userInput.value.passwordConfirmation,
            _userInput.value.username,
        )
        val passwordsMatchResult = signupUseCasesWrapper.checkPasswordsMatchUseCase(
            _userInput.value.password,
            _userInput.value.passwordConfirmation
        )

        when {
            emailValidationResult != null -> updateStateWithError(emailValidationResult)
            passwordValidationResult != null -> updateStateWithError(passwordValidationResult)
            valuesNotBlankResult != null -> updateStateWithError(valuesNotBlankResult)
            passwordsMatchResult != null -> updateStateWithError(passwordsMatchResult)
            _userInput.value.selectedOrganizationType == -1 -> updateStateWithError(
                UiText.StringResource(R.string.error_no_organization_type_selected)
            )

            _userInput.value.location == -1 -> updateStateWithError(
                UiText.StringResource(R.string.error_no_location_selected)
            )

            else -> {
                val isEmailAvailable =
                    signupUseCasesWrapper.isEmailAvailableUseCase(_userInput.value.email)
                val isUsernameAvailable =
                    signupUseCasesWrapper.isUsernameAvailableUseCase(_userInput.value.username)

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
        _uiStateFlow.update {
            UiState.Error(error)
        }
    }

    /**
     * submitValues - Submits values received by the nav back stack entry to the viewModel
     * @param token Value of the token received by the nav back stack entry
     */
    fun submitValues(
        token: String
    ) {
        onNewToken(token)
    }

    /**
     * isInvitedUser - Checks if there are all required arguments for invited users.
     * If any of the arguments is null or blank, then it's not an invited user, because
     * the navHost can only receive all the arguments together or none of them.
     * @param token Value of the token received by the nav back stack entry
     * @return false if any of the params is null or blank, true otherwise
     */
    fun isInvitedUser(
        token: String?
    ): Boolean {
        return !token.isNullOrBlank()
    }

    /**
     * isJobActive - Checks if the job is active or completed
     *
     * This function is for testing purpose only, that's why it's an
     * internal function
     * @return true if the job is active, false otherwise
     */
    internal fun isJobActive(): Boolean {
        return job?.isActive == true
    }
}
