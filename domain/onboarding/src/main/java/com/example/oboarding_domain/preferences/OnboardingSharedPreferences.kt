package com.example.oboarding_domain.preferences

interface OnboardingSharedPreferences {

    fun saveShouldShowOnboarding(shouldShow: Boolean)
    fun loadShouldShowOnboarding(): Boolean
}
