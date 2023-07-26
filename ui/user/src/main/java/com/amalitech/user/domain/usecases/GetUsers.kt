package com.amalitech.user.domain.usecases

import com.amalitech.user.repository.UserRepository

class GetUsers constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.getUsers()
}

