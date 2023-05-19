package com.example.oboarding_domain.preferences

interface IPreferences {

    fun saveShouldShowOnboarding(shouldShow: Boolean)
    fun loadShouldShowOnboarding(): Boolean

    companion object {
        const val SHOULD_SHOW_ON_BOARDING = "should_show_onboarding"
    }
}