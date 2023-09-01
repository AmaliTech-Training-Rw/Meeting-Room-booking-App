package com.amalitech.onboarding.login

import androidx.lifecycle.viewModelScope
import com.amalitech.core.domain.preferences.OnboardingSharedPreferences
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.core_ui.util.UiState
import com.amalitech.onboarding.login.use_case.LoginUseCasesWrapper
import com.amalitech.user.profile.model.dto.UserDto
import com.amalitech.user.profile.use_case.ProfileUseCaseWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sharedPreferences: OnboardingSharedPreferences,
    private val userProfileUseCaseWrapper: ProfileUseCaseWrapper,
    private val loginUseCasesWrapper: LoginUseCasesWrapper,
) : BaseViewModel<LoginUiState>() {
    private val _uiState = MutableStateFlow(
        LoginUiState()
    )
    val uiState = _uiState.asStateFlow()

    val isUsingAdminDashboard = sharedPreferences.loadAdminUserScreen()

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
            _uiStateFlow.update {
                UiState.Loading()
            }
            val emailValidation = loginUseCasesWrapper.validateEmailUseCase(_uiState.value.email)
            val passwordValidation = loginUseCasesWrapper.validatePasswordUseCase(_uiState.value.password)
            if (emailValidation == null && passwordValidation == null) {
                val apiResult = loginUseCasesWrapper.logInUseCase(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )
                val data = apiResult.data
                if (apiResult.error != null) {
                    _uiStateFlow.update {
                        UiState.Error(
                            error = apiResult.error
                        )
                    }
                } else if (data != null){
                    val isAdmin = data.isAdmin != 0

                    data.let {
                        userProfileUseCaseWrapper.saveUserUseCase(
                            UserDto(
                                uid = 0,
                                firstName = it.firstName,
                                lastName = it.lastName,
                                email = it.email,
                                title = it.title,
                                profileImgUrl = it.profileImgUrl
                            )
                        )
                    }
                    sharedPreferences.saveShouldShowOnboarding(false)
                    sharedPreferences.saveUserType(isAdmin)
                    sharedPreferences.saveLoggedInUserEmail(_uiState.value.email)
                    sharedPreferences.saveToken(data.token)
                    _uiStateFlow.update {
                        UiState.Success()
                    }
                }
            } else if (emailValidation != null) {
                _uiStateFlow.update {
                    UiState.Error(
                        error = emailValidation
                    )
                }
            } else {
                _uiStateFlow.update {
                    UiState.Error(
                        error = passwordValidation
                    )
                }
            }
        }
    }
}
