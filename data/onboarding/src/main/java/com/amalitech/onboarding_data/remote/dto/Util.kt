package com.amalitech.onboarding_data.remote.dto

import com.amalitech.core.data.data_source.remote.dto.UserData
import com.amalitech.onboarding.login.model.UserProfile

fun UserData.toProfileInfo(token: String): UserProfile {
    return UserProfile(
        email = email?:"",
        firstName = firstName?:"",
        id = id?:-1,
        isAdmin = isAdmin?:0,
        lastName = lastName?:"",
        locationId = locationId?:-1,
        organisationId = organisationId?:-1,
        userId = userId?:-1,
        username = username?:"",
        token = token
    )
}
