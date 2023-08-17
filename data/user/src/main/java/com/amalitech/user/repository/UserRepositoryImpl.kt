package com.amalitech.user.repository

import com.amalitech.user.data_source.local.UserDao
import com.amalitech.user.profile.model.Profile
import com.amalitech.user.profile.model.dto.UserDto
import com.amalitech.user.profile.repository.UserRepository

class UserRepositoryImpl(
    private val dao: UserDao,
) : UserRepository {
    override suspend fun getUser(email: String): UserDto {
        return dao.getUser(email)
    }

    override suspend fun deleteUser(user: UserDto) {
        dao.deleteUser(user)
    }

    override suspend fun insertUser(user: UserDto) {
        dao.insertUser(user)
    }

    override suspend fun updateProfile(profile: Profile) {
        // TODO("Save in the API and use the profile"
        //  "url to save into the local DB, use transactions")
    }
}
