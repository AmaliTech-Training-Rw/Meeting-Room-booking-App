package com.amalitech.user.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.amalitech.core.domain.preferences.OnboardingSharedPreferences
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.core_ui.util.UiState
import com.amalitech.user.profile.use_case.ProfileUseCaseWrapper
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val useCaseWrapper: ProfileUseCaseWrapper,
    private val sharedPref: OnboardingSharedPreferences
) : BaseViewModel<ProfileUiState>() {
    private val _isAdmin = mutableStateOf(false)
    val isAdmin: State<Boolean> get() = _isAdmin
    private val _isUsingAdminDashboard = mutableStateOf(false)
    val isUsingAdminDashboard: State<Boolean> get() = _isUsingAdminDashboard

    init {
        getUser()
    }

    internal fun getUser() {
        if (job?.isActive == true)
            return
        job = viewModelScope.launch {
            _uiStateFlow.update {
                UiState.Loading()
            }
            val userEmailAdd = sharedPref.loadLoggedInUserEmail()
            val response = useCaseWrapper.getUserUseCase(userEmailAdd)
            val data = response.data
            val error = response.error

            if (data != null) {
                _isAdmin.value = sharedPref.isUserAdmin()
                _isUsingAdminDashboard.value =
                    if (_isAdmin.value) sharedPref.loadAdminUserScreen() else false
                _uiStateFlow.update {
                    UiState.Success(
                        ProfileUiState(
                            user = data
                        )
                    )
                }
            } else if (error != null) {
                _uiStateFlow.update {
                    UiState.Error(
                        error = error
                    )
                }
            } else {
                _uiStateFlow.update {
                    UiState.Error(
                        error = UiText.StringResource(com.amalitech.core.R.string.error_default_message)
                    )
                }
            }
        }
    }

    fun updateAdminUserScreen(isUsingAdminDashboard: Boolean) {
        sharedPref.saveAdminUserScreen(isUsingAdminDashboard)
        _isUsingAdminDashboard.value = isUsingAdminDashboard
    }

    fun logout() {
        viewModelScope.launch {
            sharedPref.clear()
            val token = sharedPref.loadToken()
            useCaseWrapper.logoutUseCase(token)

        }
    }
}