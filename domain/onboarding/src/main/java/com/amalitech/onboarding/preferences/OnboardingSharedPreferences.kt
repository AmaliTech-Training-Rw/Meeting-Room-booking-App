package com.amalitech.onboarding.preferences

interface OnboardingSharedPreferences {
    fun isUserAdmin(): Boolean
    fun saveUserType(isAdmin: Boolean)
    fun saveShouldShowOnboarding(shouldShow: Boolean)
    fun loadShouldShowOnboarding(): Boolean
}
