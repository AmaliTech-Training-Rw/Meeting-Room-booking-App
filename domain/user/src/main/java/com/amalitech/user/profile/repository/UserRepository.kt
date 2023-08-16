package com.amalitech.user.profile.repository

import com.amalitech.user.profile.model.Profile
import com.amalitech.user.profile.model.dto.UserDto

interface UserRepository {
    suspend fun getUser(email: String): UserDto

    suspend fun deleteUser(user: UserDto)

    suspend fun insertUser(user: UserDto)

    suspend fun updateProfile(profile: Profile)
}
