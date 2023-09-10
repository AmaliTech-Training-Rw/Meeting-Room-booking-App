package com.amalitech.user.usecases

import com.amalitech.core.util.UiText
import com.amalitech.user.repository.UserRepository

class DeleteUserUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(userId: String): UiText? {
        return repository.deleteUser(userId)
    }
}
