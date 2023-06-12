package com.amalitech.core_ui.components

import androidx.annotation.DrawableRes

// TODO(Move this file into the admin module)
data class DashboardCardItem(
    val label: String,
    val count: Int,
    @DrawableRes val iconId: Int
)
