package com.amalitech.user.data_source.remote

import com.amalitech.core.data.data_source.remote.dto.UserData

data class UpdateProfileDto (
    val message: String?,
    val data: UserData?
)
