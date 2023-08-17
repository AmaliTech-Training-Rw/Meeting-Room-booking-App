package com.amalitech.user.usecases

import com.amalitech.user.repository.UserRepository

class GetUseCase constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.getUsers()
}

