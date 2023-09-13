package com.amalitech.user.usecases

import com.amalitech.user.models.UserToAdd
import com.amalitech.user.repository.UserRepository

class AddUserUseCase constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        user: UserToAdd
    ) = userRepository.addUser(user)
}
