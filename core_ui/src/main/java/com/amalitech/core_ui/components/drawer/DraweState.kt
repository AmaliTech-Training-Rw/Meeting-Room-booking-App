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

sealed class DrawerItem(var title:String, var icon: ImageVector){
    object Home : DrawerItem("Home", Icons.Default.Home)
    object BookingRequests : DrawerItem("Booking Requests", Icons.Default.Bookmarks)
    object Users : DrawerItem("Users", Icons.Default.People)
    object Rooms : DrawerItem("Rooms", Icons.Default.Room)
    object BookingHistory : DrawerItem("Booking History", Icons.Default.History)
    object Profile : DrawerItem("Profile", Icons.Default.PersonOutline)
    object Dashboard : DrawerItem("Dashboard", Icons.Default.Dashboard)
    object Logout : DrawerItem("Logout", Icons.Default.Home)
}