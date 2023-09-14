package com.amalitech.bookmeetingroom

import androidx.lifecycle.ViewModel
import com.amalitech.core.domain.preferences.OnboardingSharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    sharedPreferences: OnboardingSharedPreferences
) : ViewModel() {
    private var _showOnBoarding: MutableStateFlow<Boolean> =
        MutableStateFlow(sharedPreferences.loadShouldShowOnboarding())
    val showOnBoarding = _showOnBoarding.asStateFlow()
}
