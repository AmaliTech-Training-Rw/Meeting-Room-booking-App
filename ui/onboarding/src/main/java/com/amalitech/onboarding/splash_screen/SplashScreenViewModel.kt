package com.amalitech.onboarding.splash_screen

import androidx.lifecycle.ViewModel
import com.amalitech.core.domain.preferences.OnboardingSharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SplashScreenViewModel(
    private val preferences: OnboardingSharedPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { splashScreenUiState ->
            val isUsingAdminDashboard = preferences.loadAdminUserScreen()
            splashScreenUiState.copy(
                hasFinishedChecking = true,
                isUsingAdminDashboard = isUsingAdminDashboard
            )
        }
    }
}
