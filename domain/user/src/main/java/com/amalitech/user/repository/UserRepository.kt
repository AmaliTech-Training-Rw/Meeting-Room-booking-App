package com.amalitech.user.repository

import com.amalitech.user.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<User>
    fun addUser(user: User) // should this suspend?
}