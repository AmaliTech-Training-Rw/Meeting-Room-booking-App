package com.amalitech.user.profile.use_case

import android.content.Context
import com.amalitech.user.profile.model.Profile
import com.amalitech.user.repository.UserRepository

class UpdateProfileUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(profile: Profile, context: Context) =
        userRepository.updateProfile(profile, context)

}
