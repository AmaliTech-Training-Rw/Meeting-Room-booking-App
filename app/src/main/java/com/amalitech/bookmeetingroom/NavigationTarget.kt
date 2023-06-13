package com.amalitech.bookmeetingroom

sealed class NavigationTarget (val route:String){
    object DEBUG :NavigationTarget("debug")
    object LOGIN: NavigationTarget("Login")
    object DASHBOARD: NavigationTarget("Dashboard")
    object FORGOT: NavigationTarget("Forgot password")
    object RESET: NavigationTarget("Reset password")
    object ONBOARDING: NavigationTarget("On Boarding")
    object SPLASH: NavigationTarget("Splash")
    object DASHBOARD_CARD: NavigationTarget("Dashboard Card")
    object DASHBOARD_GRAPH: NavigationTarget("Dashboard Graph")
}
