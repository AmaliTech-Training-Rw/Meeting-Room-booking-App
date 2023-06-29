package com.amalitech.bookmeetingroom.navigation

sealed class NavigationTarget (val route:String){
    object DEBUG : NavigationTarget("debug")
    object LOGIN: NavigationTarget("Login")
    object FORGOT: NavigationTarget("Forgot password")
    object RESET: NavigationTarget("Reset password")
    object ONBOARD: NavigationTarget("OnBoarding")
    object SPLASH: NavigationTarget("Splash")
    object CARD: NavigationTarget("Dashboard Card")
    object DASHBOARD: NavigationTarget("Dashboard Graph")
    object DRAWER: NavigationTarget("Drawer")
    object ADDROOM: NavigationTarget("Add Room")
    object  CALENDAR: NavigationTarget("Calendar Screen")
    object HOME: NavigationTarget("Home Screen")
    object SIGNUP: NavigationTarget("Signup")
}
