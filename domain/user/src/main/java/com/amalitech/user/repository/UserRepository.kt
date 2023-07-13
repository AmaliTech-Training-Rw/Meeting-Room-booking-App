package com.amalitech.user.repository

import com.amalitech.user.model.dto.UserDto

interface UserRepository {
    suspend fun getUser(id: String): UserDto

    suspend fun deleteUser(user: UserDto)

    suspend fun insertUser(user: UserDto)
}
