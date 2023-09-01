package com.amalitech.user.usecases

import com.amalitech.user.models.User
import com.amalitech.user.repository.UserRepository

class AddUserUseCase constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        user: User
    ) = userRepository.inviteUser(user)
}
