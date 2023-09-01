package com.amalitech.user.data_source.local

import kotlinx.coroutines.flow.Flow

class UserCacheImpl(
    private val dao: UserDao,
) : UserCache {
    override fun getUsers(): Flow<List<UsersEntity>> {
        return dao.getAllUsers()
    }

    override suspend fun storeUsers(users: List<UsersEntity>) {
        dao.insertUsers(users)
    }
}