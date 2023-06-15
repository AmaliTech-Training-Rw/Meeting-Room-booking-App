package com.amalitech.onboarding.login

import androidx.lifecycle.viewModelScope
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.core_ui.util.UiState
import com.amalitech.onboarding.login.use_case.LoginUseCase
import com.amalitech.onboarding.preferences.OnboardingSharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val sharedPreferences: OnboardingSharedPreferences
) : BaseViewModel<LoginUiState>() {
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
        if (job?.isActive == true)
            return
        job = viewModelScope.launch {
            privateBaseResult.update {
                UiState.Loading()
            }
            val emailValidation = loginUseCase.validateEmail(_uiState.value.email)
            val passwordValidation = loginUseCase.validatePassword(_uiState.value.password)
            if (emailValidation == null && passwordValidation == null) {
                val apiResult = loginUseCase.logIn(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )
                if (apiResult != null) {
                    privateBaseResult.update {
                        UiState.Error(
                            error = apiResult
                        )
                    }
                } else {
                    val isAdmin = loginUseCase.isUserAdmin()
                    sharedPreferences.saveShouldShowOnboarding(false)
                    sharedPreferences.saveUserType(isAdmin)
                    privateBaseResult.update {
                        UiState.Success()
                    }
                }
            } else if (emailValidation != null) {
                privateBaseResult.update {
                    UiState.Error(
                        error = emailValidation
                    )
                }
            } else {
                privateBaseResult.update {
                    UiState.Error(
                        error = passwordValidation
                    )
                }
            }
        }
    }
}
