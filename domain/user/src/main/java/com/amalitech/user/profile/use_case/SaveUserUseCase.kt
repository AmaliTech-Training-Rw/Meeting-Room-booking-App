package com.amalitech.user.profile.use_case

import com.amalitech.user.profile.model.dto.UserDto
import com.amalitech.user.repository.UserRepository

class SaveUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userDto: UserDto) {
        userRepository.insertUser(userDto)
    }
}
