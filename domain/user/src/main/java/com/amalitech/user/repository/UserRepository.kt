package com.amalitech.user.repository

import android.content.Context
import com.amalitech.core.domain.model.UserProfile
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.user.models.User
import com.amalitech.user.models.UserToAdd
import com.amalitech.user.profile.model.Profile
import com.amalitech.user.profile.model.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(email: String): UserDto

    suspend fun deleteUser(userId: String): UiText?

    suspend fun saveLoggedInUser(user: UserDto)

    suspend fun getUsers(isInviting: Boolean): ApiResult<Flow<List<User>>>

    suspend fun addUser(user: UserToAdd): UiText?

    suspend fun updateProfile(profile: Profile, context: Context): ApiResult<UserProfile>

    suspend fun logout(token: String)
}
