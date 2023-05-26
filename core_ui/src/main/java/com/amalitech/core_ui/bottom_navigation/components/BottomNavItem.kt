package com.amalitech.core_ui.bottom_navigation.components

import androidx.annotation.DrawableRes
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.R

data class BottomNavItem(
    val label: UiText,
    @DrawableRes val icon: Int,
    val index: Int,
    val badge: BottomNavBadge.CountBadge = BottomNavBadge.CountBadge(0),
) {
    companion object {
        val items = mutableListOf(
            BottomNavItem(
                UiText.StringResource(R.string.home),
                R.drawable.baseline_home_24,
                index = NavItem.Home.index
            ),
            BottomNavItem(
                UiText.StringResource(R.string.profile),
                R.drawable.baseline_person_outline_24,
                index = NavItem.Profile.index
            ),
            BottomNavItem(
                UiText.StringResource(R.string.bookings),
                R.drawable.baseline_book_24,
                index = NavItem.Bookings.index
            ),
            BottomNavItem(
                UiText.StringResource(R.string.invitation),
                R.drawable.baseline_mail_outline_24,
                index = NavItem.Invitations.index,
                badge = BottomNavBadge.CountBadge(0)
            ),
        )
    }
}

enum class NavItem(val index: Int) {
    Home(0),
    Profile(1),
    Bookings(2),
    Invitations(3),
}

sealed class BottomNavBadge {
    data class CountBadge(val count: Int) : BottomNavBadge()
}
