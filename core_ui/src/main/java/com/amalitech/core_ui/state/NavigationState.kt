package com.amalitech.core_ui.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Room
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var title:String, var route:String, var icon: ImageVector){
    object Home : NavigationItem("Home", "home_route", Icons.Default.Home)
    object BookingRequests :
        NavigationItem("Booking Requests", "my_bookings_route", Icons.Default.Bookmarks)

    object Users : NavigationItem("Users", "users_route", Icons.Default.People)
    object Rooms : NavigationItem("Rooms", "rooms_route", Icons.Default.Room)
    object BookingHistory :
        NavigationItem("Booking History", "booking_history_route", Icons.Default.History)

    object Profile : NavigationItem("Profile", "profile_route", Icons.Default.PersonOutline)
    object Dashboard : NavigationItem("Dashboard", "dashboard_route", Icons.Default.Dashboard)
    object Invitations : NavigationItem("invitations", "invitations_route", Icons.Default.Home)
    object Logout : NavigationItem("Logout", "logout_route", Icons.Default.Home)
    object BookingRequestDetail : NavigationItem("Booking request detail", "booking_request_detail", Icons.Default.Details)

    companion object {
        fun createItems(): List<NavigationItem> = listOf(
            Home,
            BookingRequests,
            Users,
            Rooms,
            BookingHistory,
            Profile,
            Dashboard,
            Invitations,
            Logout
        )
    }
}