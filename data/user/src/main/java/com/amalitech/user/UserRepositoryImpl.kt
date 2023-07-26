package com.amalitech.user

import com.amalitech.user.models.User
import com.amalitech.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

// TODO: inject remote and cache here (or som kinda data source)
class UserRepositoryImpl: UserRepository {
    override fun getUsers(): Flow<User> = flowOf(
        User(
            "cool",
            "User Name",
            "example@gmail.com",
            true
        ),
        User(
            "cool",
            "User Name",
            "example@gmail.com",
            true
        ),
        User(
            "cool",
            "User Name",
            "example@gmail.com",
            true
        ),
        User(
            "cool",
            "User Name",
            "example@gmail.com",
            true
        )
    )
}
