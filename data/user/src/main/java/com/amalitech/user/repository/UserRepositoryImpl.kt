package com.amalitech.user.repository

import com.amalitech.user.data_source.local.UserDao
import com.amalitech.user.model.dto.UserDto

class UserRepositoryImpl(
    private val dao: UserDao
): UserRepository {
    override suspend fun getUser(id: String): UserDto {
        return dao.getUser(id)
    }

    override suspend fun deleteUser(user: UserDto) {
        dao.deleteUser(user)
    }

    override suspend fun insertUser(user: UserDto) {
        dao.insertUser(user)
    }
}
