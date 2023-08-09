package com.amalitech.user

import com.amalitech.user.models.User
import com.amalitech.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

// TODO: inject remote and cache here (or some kinda data source)
class UserRepositoryImpl: UserRepository {
    override fun getUsers(): Flow<User> = flowOf(
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
            "4",
            "cool",
            "User Name",
            "example@gmail.com",
            false
        )
    )

    override fun addUser(user: User) {
        // TODO: connect to the data source
    }
}
