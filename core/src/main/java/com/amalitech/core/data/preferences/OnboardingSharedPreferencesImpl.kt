package com.amalitech.core.data.preferences

import android.content.SharedPreferences
import com.amalitech.core.domain.preferences.OnboardingSharedPreferences

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
                isAdmin
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

    override fun saveLoggedInUserEmail(email: String) {
        sharedPreferences.edit().putString(
            EMAIL_ADDRESS,
            email
        ).apply()
    }

    override fun loadLoggedInUserEmail(): String {
        return sharedPreferences.getString(
            EMAIL_ADDRESS,
            ""
        ) ?: ""
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    override fun saveAdminUserScreen(isUsingAdminDashboard: Boolean) {
        sharedPreferences.edit().putBoolean(
            ADMIN_USER_SCREEN,
            isUsingAdminDashboard
        ).apply()
    }

    override fun loadAdminUserScreen(): Boolean {
        return sharedPreferences.getBoolean(
            ADMIN_USER_SCREEN,
            true
        )
    }

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString(
            TOKEN,
            token
        ).apply()
    }

    override fun loadToken(): String {
        return sharedPreferences.getString(
            TOKEN,
            ""
        ) ?: ""
    }

    companion object {
        const val SHOULD_SHOW_ON_BOARDING = "should_show_onboarding"
        const val IS_USER_ADMIN = "is_user_admin"
        const val EMAIL_ADDRESS = "email_address"
        const val ADMIN_USER_SCREEN = "admin_user_screen"
        const val TOKEN = "token"
    }
}
