package com.amalitech.user.data_source.local.cache

import com.amalitech.user.data_source.local.UsersEntity
import kotlinx.coroutines.flow.Flow

interface UserCache {
    fun getUsers(): Flow<List<UsersEntity>>
    suspend fun storeUsers(users: List<UsersEntity>)
}