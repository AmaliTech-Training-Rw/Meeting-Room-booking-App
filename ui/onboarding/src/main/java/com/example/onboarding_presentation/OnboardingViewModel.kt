package com.example.onboarding_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oboarding_domain.preferences.OnboardingSharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val onboardingSharedPreferences: OnboardingSharedPreferences
) : ViewModel() {

    private val _finishedOnboarding = MutableStateFlow(false)
    val finishedSaving = _finishedOnboarding

    fun onGetSarted() {
        viewModelScope.launch {
            onboardingSharedPreferences.saveShouldShowOnboarding(false)
            _finishedOnboarding.value = true
        }
    }
}