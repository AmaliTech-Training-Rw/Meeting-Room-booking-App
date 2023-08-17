package com.amalitech.user.repository

import com.amalitech.user.models.User
import com.amalitech.user.profile.model.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(email: String): UserDto

    suspend fun deleteUser(user: UserDto)

    suspend fun insertUser(user: UserDto)

    suspend fun getUsers(): Flow<User>

    suspend fun addUser(user: User)
}
