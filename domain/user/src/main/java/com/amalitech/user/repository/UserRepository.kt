package com.amalitech.user.repository

import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.user.models.User
import com.amalitech.user.models.UserToAdd
import com.amalitech.user.profile.model.Profile
import com.amalitech.user.profile.model.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(email: String): UserDto

    suspend fun deleteUser(user: UserDto)

    suspend fun insertUser(user: UserDto)

    suspend fun getUsers(): ApiResult<Flow<List<User>>>

    suspend fun addUser(user: UserToAdd): UiText?

    suspend fun updateProfile(profile: Profile)

    suspend fun logout(token: String)
}
