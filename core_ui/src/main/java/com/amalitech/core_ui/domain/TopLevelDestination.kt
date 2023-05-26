package com.amalitech.core_ui.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.amalitech.core_ui.R.string.booking_history
import com.amalitech.core_ui.R.string.booking_requests
import com.amalitech.core_ui.R.string.dashboard
import com.amalitech.core_ui.R.string.home
import com.amalitech.core_ui.R.string.logout
import com.amalitech.core_ui.R.string.profile
import com.amalitech.core_ui.R.string.rooms
import com.amalitech.core_ui.R.string.users
import com.amalitech.core_ui.R.string.invitations

enum class TopLevelDestination(
    val icon: ImageVector,
    val titleTextId: Int
) {
    HOME(
        icon = Icons.Filled.Menu,
        titleTextId = home
    ),

    BOOKING_REQUESTS(
        icon = Icons.Filled.Menu,
        titleTextId = booking_requests
    ),

    USERS(
        icon = Icons.Filled.Menu,
        titleTextId = users
    ),

    ROOMS(
        icon = Icons.Filled.Menu,
        titleTextId = rooms
    ),

    BOOKING_HISTORY(
        icon = Icons.Filled.Menu,
        titleTextId = booking_history
    ),

    PROFILE(
        icon = Icons.Filled.Menu,
        titleTextId = profile
    ),

    DASHBOARD(
        icon = Icons.Filled.Menu,
        titleTextId = dashboard
    ),

    INVITATIONS(
        icon = Icons.Filled.Menu,
        titleTextId = invitations
    ),

    LOGOUT(
        icon = Icons.Filled.Menu,
        titleTextId = logout
    )
}