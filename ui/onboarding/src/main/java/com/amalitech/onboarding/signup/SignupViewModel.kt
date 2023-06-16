package com.amalitech.onboarding.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.amalitech.core.R
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.AuthenticationBaseViewModel
import com.amalitech.core_ui.util.UiState
import com.amalitech.onboarding.signup.model.User
import com.amalitech.onboarding.signup.use_case.SignupUseCase
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel(
    private val signupUseCase: SignupUseCase
) : AuthenticationBaseViewModel<SignupUiState>() {

    private val _userInput = mutableStateOf(UserInput())
    val userInput: State<UserInput>
        get() = _userInput


    init {
        fetchOrganizations()
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
     * @param location the location entered by the user
     */
    fun onNewLocation(location: String) {
        _userInput.value = _userInput.value.copy(
            location = location.trim()
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

    fun onSelectedOrganizationType(type: String) {
        _userInput.value = _userInput.value.copy(
           selectedOrganizationType = type
        )
    }

    private fun fetchOrganizations() {
        if (job?.isActive == true)
            return

        job = viewModelScope.launch {
            baseResult.update {
                UiState.Loading()
            }
            val result = signupUseCase.fetchOrganizationsType()
            if (result.data != null) {
                baseResult.update {
                    UiState.Success(
                        SignupUiState(result.data!!)
                    )
                }
            } else if (result.error != null) {
                baseResult.update {
                    UiState.Error(
                        result.error!!
                    )
                }
            } else {
                baseResult.update {
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
            baseResult.update {
                UiState.Loading()
            }
            validateData()
            if (baseResult.value !is UiState.Error) {
                val result = signupUseCase.signup(
                    user = User(
                        _userInput.value.username,
                        _userInput.value.organizationName,
                        _userInput.value.email,
                        _userInput.value.selectedOrganizationType,
                        _userInput.value.location,
                        _userInput.value.password,
                        _userInput.value.passwordConfirmation,
                    )
                )
                if (result == null) {
                    baseResult.update {
                        UiState.Success(SignupUiState(shouldNavigate = true))
                    }
                } else {
                    baseResult.update {
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
        val emailValidationResult = signupUseCase.validateEmail(_userInput.value.email)
        val passwordValidationResult = signupUseCase.validatePassword(_userInput.value.password)
        val valuesNotBlankResult = signupUseCase.checkValuesNotBlank(
            _userInput.value.email,
            _userInput.value.password,
            _userInput.value.passwordConfirmation,
            _userInput.value.location,
            _userInput.value.username,
            _userInput.value.selectedOrganizationType
        )
        val passwordsMatchResult = signupUseCase.checkPasswordsMatch(
            _userInput.value.password,
            _userInput.value.passwordConfirmation
        )

        when {
            emailValidationResult != null -> updateStateWithError(emailValidationResult)
            passwordValidationResult != null -> updateStateWithError(passwordValidationResult)
            valuesNotBlankResult != null -> updateStateWithError(valuesNotBlankResult)
            passwordsMatchResult != null -> updateStateWithError(passwordsMatchResult)
            _userInput.value.selectedOrganizationType.isBlank() -> updateStateWithError(
                UiText.StringResource(R.string.error_no_organization_type_selected)
            )

            else -> {
                val isEmailAvailable = signupUseCase.isEmailAvailable(_userInput.value.email)
                val isUsernameAvailable = signupUseCase.isUsernameAvailable(_userInput.value.username)

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
        baseResult.update {
            UiState.Error(error)
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
