package com.amalitech.core.domain.preferences

interface OnboardingSharedPreferences {
    fun isUserAdmin(): Boolean
    fun saveUserType(isAdmin: Boolean)
    fun saveShouldShowOnboarding(shouldShow: Boolean)
    fun loadShouldShowOnboarding(): Boolean

    fun saveLoggedInUserEmail(email: String)
    fun loadLoggedInUserEmail(): String
    fun clear()
    fun saveAdminUserScreen(isUsingAdminDashboard: Boolean)
    fun loadAdminUserScreen(): Boolean
}
