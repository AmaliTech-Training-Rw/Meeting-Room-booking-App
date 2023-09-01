package com.amalitech.user.usecases

import com.amalitech.user.repository.UserRepository

// this fetches from the local data source
class GetUserCase constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.getUsers()
}

