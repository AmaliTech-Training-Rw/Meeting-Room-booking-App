package com.example.onboarding_data.preferences

import android.content.SharedPreferences
import com.example.oboarding_domain.preferences.IPreferences

class PreferencesImpl(
    private val sharedPreferences: SharedPreferences
): IPreferences {
    override fun saveShouldShowOnboarding(shouldShow: Boolean) {
        sharedPreferences
            .edit()
            .putBoolean(IPreferences.SHOULD_SHOW_ON_BOARDING, shouldShow)
            .apply()
    }

    override fun loadShouldShowOnboarding() = sharedPreferences
        .getBoolean(
            IPreferences.SHOULD_SHOW_ON_BOARDING,
            true
        )
}