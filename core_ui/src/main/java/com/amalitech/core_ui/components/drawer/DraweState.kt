package com.amalitech.core_ui.components.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Room
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DrawerItem(var title:String, var route:String, var icon: ImageVector){
    object Home : DrawerItem("Home", "home_route", Icons.Default.Home)
    object BookingRequests : DrawerItem("Booking Requests", "my_bookings_route" ,Icons.Default.Bookmarks)
    object Users : DrawerItem("Users", "users_route" ,Icons.Default.People)
    object Rooms : DrawerItem("Rooms","rooms_route" ,Icons.Default.Room)
    object BookingHistory : DrawerItem("Booking History", "booking_history_route" ,Icons.Default.History)
    object Profile : DrawerItem("Profile", "profile_route" ,Icons.Default.PersonOutline)
    object Dashboard : DrawerItem("Dashboard","dashboard_route" ,Icons.Default.Dashboard)
    object Invitations : DrawerItem("invitations","invitations_route" ,Icons.Default.Home)
    object Logout : DrawerItem("Logout","logout_route" ,Icons.Default.Home)
}