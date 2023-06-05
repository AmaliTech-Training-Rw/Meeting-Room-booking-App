package com.amalitech.onboarding_data.preferences

import android.content.SharedPreferences
import com.amalitech.onboarding.preferences.OnboardingSharedPreferences

class OnboardingSharedPreferencesImpl(
    private val sharedPreferences: SharedPreferences
) : OnboardingSharedPreferences {
    override fun isUserAdmin(): Boolean {
        return sharedPreferences.getBoolean(
            IS_USER_ADMIN,
            false
        )
    }

    override fun saveUserType(isAdmin: Boolean) {
        sharedPreferences.edit()
            .putBoolean(
                IS_USER_ADMIN,
                false
            )
            .apply()
    }

    override fun saveShouldShowOnboarding(shouldShow: Boolean) {
        sharedPreferences.edit().putBoolean(
            SHOULD_SHOW_ON_BOARDING,
            shouldShow
        ).apply()
    }

    override fun loadShouldShowOnboarding() = sharedPreferences.getBoolean(
        SHOULD_SHOW_ON_BOARDING,
        true
    )

    companion object {
        const val SHOULD_SHOW_ON_BOARDING = "should_show_onboarding"
        const val IS_USER_ADMIN = "is_user_admin"
    }
}
