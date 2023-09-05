package com.amalitech.user.usecases

import android.util.Log
import com.amalitech.user.models.User
import com.amalitech.user.repository.UserRepository

// this can be used to refresh the users list
class FetchRemoteUsersCase constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke( ): List<User> {
        Log.d("TAG", "getRemoteUsers 3:")
        val fetchedUsers = userRepository.getRemoteUsers()
        Log.d("TAG", "getRemoteUsers 1: ${fetchedUsers.size}")
        // this is where the caching happens
        userRepository.insertUsers(fetchedUsers)
        return fetchedUsers
    }
}