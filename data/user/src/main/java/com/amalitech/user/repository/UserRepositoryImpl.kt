package com.amalitech.user.repository

import com.amalitech.user.data_source.local.UserDao
import com.amalitech.user.models.User
import com.amalitech.user.profile.model.Profile
import com.amalitech.user.profile.model.dto.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

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

    override suspend fun getUsers(): Flow<User> = flowOf(
        User(
            "1",
            "cool",
            "User Name",
            "example@gmail.com",
            true
        ),
        User(
            "2",
            "cool",
            "User Name",
            "example@gmail.com",
            false
        ),
        User(
            "3",
            "cool",
            "User Name",
            "example@gmail.com",
            true
        ),
        User(
            "4",
            "cool",
            "User Name",
            "example@gmail.com",
            true
        ),
        User(
            "5",
            "cool",
            "User Name",
            "example@gmail.com",
            false
        )
    )

    override suspend fun addUser(user: User) {
        // TODO: connect to the data source
    }

    override suspend fun updateProfile(profile: Profile) {
        //TODO("Not yet implemented")
    }
//    override suspend fun updateProfile(profile: Profile) {
//        // TODO("Save in the API and use the profile"
//        //  "url to save into the local DB, use transactions")
//    }
}
