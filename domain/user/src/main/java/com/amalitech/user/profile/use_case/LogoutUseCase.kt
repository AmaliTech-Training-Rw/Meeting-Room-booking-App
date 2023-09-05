package com.amalitech.user.profile.use_case

import com.amalitech.user.repository.UserRepository

class LogoutUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(token: String) {
        userRepository.logout(token)
    }
}
