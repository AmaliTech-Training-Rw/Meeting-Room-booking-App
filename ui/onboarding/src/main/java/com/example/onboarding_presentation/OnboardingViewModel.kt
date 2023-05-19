package com.example.onboarding_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oboarding_domain.preferences.IPreferences
import kotlinx.coroutines.channels.Channel
import com.amalitech.core.util.UiEvents
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val pref: IPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: OnboardingEvent) {
        viewModelScope.launch(dispatcher) {
            when (event) {
                OnboardingEvent.OnGetStartClick -> {
                    pref.saveShouldShowOnboarding(false)
                    _uiEvent.send(
                        UiEvents.NavigateToLogin
                    )
                }
            }
        }
    }
}