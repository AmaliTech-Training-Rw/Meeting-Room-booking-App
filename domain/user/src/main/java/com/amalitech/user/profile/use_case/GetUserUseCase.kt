package com.amalitech.user.profile.use_case

import com.amalitech.core.util.Response
import com.amalitech.core.util.UiText
import com.amalitech.user.profile.model.dto.UserDto
import com.amalitech.user.repository.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(email: String): Response<UserDto> {
        var response = Response<UserDto>()
        response = try {
            response.copy(data = userRepository.getUser(email))
        } catch (e: Exception) {
            response.copy(
                error = e.localizedMessage?.let { UiText.DynamicString(it) }
                    ?: UiText.StringResource(
                        com.amalitech.core.R.string.error_default_message
                    )
            )
        }
        return response
    }
}
