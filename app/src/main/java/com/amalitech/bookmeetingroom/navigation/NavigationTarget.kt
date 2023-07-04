package com.amalitech.bookmeetingroom.navigation

sealed class NavigationTarget (val route:String){
    object DEBUG : NavigationTarget("debug")
    object LOGIN: NavigationTarget("Login")
    object FORGOT: NavigationTarget("Forgot password")
    object RESET: NavigationTarget("Reset password")
    object ONBOARD: NavigationTarget("OnBoarding")
    object SPLASH: NavigationTarget("Splash")
    object CARD: NavigationTarget("Dashboard Card")
    object DASHBOARD: NavigationTarget("Dashboard")
    object DRAWER: NavigationTarget("Drawer")
    object ADDROOM: NavigationTarget("Add Room")
<<<<<<< HEAD
    object APPBAR: NavigationTarget("Top app bar")
=======
>>>>>>> 6507536a7f189548f27409bce6618d0f23dd22ad
    object  CALENDAR: NavigationTarget("Calendar Screen")
    object HOME: NavigationTarget("Home Screen")
    object SIGNUP: NavigationTarget("Signup")
}
