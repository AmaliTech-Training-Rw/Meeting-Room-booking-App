package com.amalitech.rooms

import android.net.Uri
import com.amalitech.core.util.UiText
import com.amalitech.onboarding.signup.model.LocationX

data class AddRoomUiState(
    val name: String = "",
    val capacity: Int = 1,
    val location: Int = -1,
    val locationList: List<LocationX>? = listOf(),
    val imagesList: List<Uri> = emptyList(),
    val features: List<String> = listOf(),
    val error: UiText? = null,
    val canNavigate: Boolean = false,
    val feature: String = ""
)
