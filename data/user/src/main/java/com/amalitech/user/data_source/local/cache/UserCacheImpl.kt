package com.amalitech.user.data_source.local.cache

import com.amalitech.user.data_source.local.UserDao
import com.amalitech.user.data_source.local.UsersEntity
import kotlinx.coroutines.flow.Flow

class UserCacheImpl(
    private val dao: UserDao,
)  : UserCache {
    override fun getUsers(): Flow<List<UsersEntity>> {
        return dao.getAllUsers()
    }

    override suspend fun storeUsers(users: List<UsersEntity>) {
        dao.insertUsers(users)
    }
}