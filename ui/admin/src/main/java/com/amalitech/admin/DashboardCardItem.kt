package com.amalitech.admin

import androidx.annotation.DrawableRes

data class DashboardCardItem(
    val label: String,
    val count: Int,
    @DrawableRes val iconId: Int
)
