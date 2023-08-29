package com.amalitech.user.profile.use_case

import com.amalitech.core.R
import com.amalitech.core.util.UiText
import com.amalitech.user.profile.model.Profile
import com.amalitech.user.repository.UserRepository
import kotlinx.coroutines.delay

class UpdateProfileUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(profile: Profile): UiText? {
        delay(3000)
        try {
            userRepository.updateProfile(profile)
        } catch (e: Exception) {
            return if (e.localizedMessage != null)
                e.localizedMessage?.let { UiText.DynamicString(it) }
            else UiText.StringResource(R.string.error_default_message)
        }
        return null
    }
}
