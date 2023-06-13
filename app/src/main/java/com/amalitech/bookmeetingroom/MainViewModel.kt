package com.amalitech.bookmeetingroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.core_ui.util.SnackbarManager
import com.amalitech.core_ui.util.SnackbarMessage.Companion.toSnackbarMessage
import com.amalitech.onboarding.preferences.OnboardingSharedPreferences
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    sharedPreferences: OnboardingSharedPreferences
) : ViewModel() {
    private var _showOnBoarding: MutableStateFlow<Boolean> = MutableStateFlow(sharedPreferences.loadShouldShowOnboarding())
    val showOnBoarding = _showOnBoarding.asStateFlow()

    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
            },
            block = block
        )
}
