package com.amalitech.onboarding_domain.preferences

interface OnboardingSharedPreferences {

    fun saveShouldShowOnboarding(shouldShow: Boolean)
    fun loadShouldShowOnboarding(): Boolean
}
