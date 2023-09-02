package com.amalitech.user.repository

import com.amalitech.user.models.User
import com.amalitech.user.profile.model.Profile
import com.amalitech.user.profile.model.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getRemoteUsers(): List<User>
    suspend fun getLocalUsers(): Flow<List<User>>
    suspend fun insertUsers(users: List<User>)
    suspend fun inviteUser(user: User)

    suspend fun getUser(email: String): UserDto
    suspend fun deleteUser(user: UserDto)
    suspend fun insertUser(user: UserDto)
    suspend fun updateProfile(profile: Profile)
}
