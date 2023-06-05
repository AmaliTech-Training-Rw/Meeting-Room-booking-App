package com.amalitech.onboarding.login

import androidx.lifecycle.ViewModel
import com.amalitech.core.util.UiText
import com.amalitech.onboarding.login.use_case.LoginUseCase
import com.amalitech.onboarding.preferences.OnboardingSharedPreferences
import com.amalitech.ui.onboarding.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val sharedPreferences: OnboardingSharedPreferences
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        LoginUiState()
    )
    val uiState = _uiState.asStateFlow()

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
     * onLoginClick - Validates given credentials via use cases and log the user in
     *
     * If any use case returns
     * an instance of UiText, there is a message/error that need
     * to be displayed for the user. Otherwise, everything worked
     * fine.
     */
    fun onLoginClick() {
        val emailValidation = loginUseCase.validateEmail(_uiState.value.email)
        val passwordValidation = loginUseCase.validatePassword(_uiState.value.password)
        if (emailValidation == null && passwordValidation == null) {
            val result = loginUseCase.logIn(
                email = _uiState.value.email,
                password = _uiState.value.password
            )
            if (result != null) {
                _uiState.update { loginUiState ->
                    loginUiState.copy(
                        error = result
                    )
                }
            } else {
                val isAdmin = loginUseCase.isUserAdmin()
                sharedPreferences.saveShouldShowOnboarding(false)
                sharedPreferences.saveUserType(isAdmin)
                _uiState.update { loginUiState ->
                    loginUiState.copy(
                        snackBarValue = UiText.StringResource(R.string.logged_in_successfully),
                        finishedLoggingIn = true
                    )
                }

            }
        } else if (emailValidation != null) {
            _uiState.update { loginUiState ->
                loginUiState.copy(
                    error = emailValidation
                )
            }
        } else {
            _uiState.update { loginUiState ->
                loginUiState.copy(
                    error = passwordValidation
                )
            }
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
}
