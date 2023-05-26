package com.amalitech.onboarding_data.preferences

import android.content.SharedPreferences
import com.amalitech.onboarding.preferences.OnboardingSharedPreferences

class OnboardingSharedPreferencesImpl(
    private val sharedPreferences: SharedPreferences
) : OnboardingSharedPreferences {
    override fun saveShouldShowOnboarding(shouldShow: Boolean) {
        sharedPreferences.edit().putBoolean(com.amalitech.onboarding_data.preferences.OnboardingSharedPreferencesImpl.Companion.SHOULD_SHOW_ON_BOARDING, shouldShow).apply()
    }

    override fun loadShouldShowOnboarding() = sharedPreferences.getBoolean(
        com.amalitech.onboarding_data.preferences.OnboardingSharedPreferencesImpl.Companion.SHOULD_SHOW_ON_BOARDING, true
        )

    companion object {
        const val SHOULD_SHOW_ON_BOARDING = "should_show_onboarding"
    }
}
