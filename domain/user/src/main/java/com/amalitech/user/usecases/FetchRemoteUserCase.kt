package com.amalitech.user.usecases

import com.amalitech.user.models.User
import com.amalitech.user.repository.UserRepository

// this can be used to refresh the users list
class FetchRemoteUsersCase constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke( ): List<User> {
        val fetchedUsers = userRepository.getRemoteUsers()
        // this is where the caching happens
        userRepository.insertUsers(fetchedUsers)
        return fetchedUsers
    }
}

