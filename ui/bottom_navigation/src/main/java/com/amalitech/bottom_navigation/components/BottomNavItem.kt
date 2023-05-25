package com.amalitech.bottom_navigation.components

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.amalitech.core.util.UiText
import com.amalitech.ui.bottom_navigation.R

data class BottomNavItem(
    val label: UiText,
    @DrawableRes val icon: Int,
    val index: Int,
    val badge: Int? = null
) {
    companion object {
        val items = mutableListOf(
            BottomNavItem(
                UiText.StringResource(R.string.home),
                R.drawable.baseline_home_24,
                index = 0
            ),
            BottomNavItem(
                UiText.StringResource(R.string.profile),
                R.drawable.baseline_person_outline_24,
                index = 1
            ),
            BottomNavItem(
                UiText.StringResource(R.string.my_bookings),
                R.drawable.baseline_book_24,
                index = 2
            ),
            BottomNavItem(
                UiText.StringResource(R.string.invitation),
                R.drawable.baseline_mail_outline_24,
                index = 3,
                badge = 4
            ),
        )

        const val INVITATION_INDEX = 3
    }
}


