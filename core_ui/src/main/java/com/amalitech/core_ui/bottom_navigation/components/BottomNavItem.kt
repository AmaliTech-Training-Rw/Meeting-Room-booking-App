package com.amalitech.core_ui.bottom_navigation.components

import androidx.annotation.DrawableRes
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.R

sealed class BottomNavItem(
    val label: UiText,
    @DrawableRes val icon: Int,
    val route: String,
    var badge: BottomNavBadge.CountBadge = BottomNavBadge.CountBadge(0)
) {
    object Home : BottomNavItem(
        label = UiText.StringResource(R.string.home),
        icon = R.drawable.baseline_home_24,
        route = "home"
    )

    object Profile : BottomNavItem(
        label = UiText.StringResource(R.string.profile),
        icon = R.drawable.baseline_person_outline_24,
        route = "profile"
    )

    object Bookings : BottomNavItem(
        label = UiText.StringResource(R.string.bookings),
        icon = R.drawable.baseline_book_24,
        route = "bookings"
    )

    object Invitations : BottomNavItem(
        label = UiText.StringResource(R.string.invitation),
        icon = R.drawable.baseline_mail_outline_24,
        route = "invitations",
        badge = BottomNavBadge.CountBadge(0)
    )

    companion object {
        fun createItems(): List<BottomNavItem> = listOf(
            Home,
            Profile,
            Bookings,
            Invitations
        )
    }
}

sealed class BottomNavBadge {
    data class CountBadge(val count: Int) : BottomNavBadge()
}
