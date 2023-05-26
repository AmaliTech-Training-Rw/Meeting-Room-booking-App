package com.amalitech.onboarding.preferences

interface OnboardingSharedPreferences {

    fun saveShouldShowOnboarding(shouldShow: Boolean)
    fun loadShouldShowOnboarding(): Boolean
}
