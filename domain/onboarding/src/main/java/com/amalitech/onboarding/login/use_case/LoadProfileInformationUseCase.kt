package com.amalitech.onboarding.login.use_case

import com.amalitech.core.util.Response
import com.amalitech.onboarding.login.model.UserProfile
import kotlinx.coroutines.delay

class LoadProfileInformationUseCase {
    suspend operator fun invoke(email: String): Response<UserProfile> {
        delay(200)
        return Response(
            data = UserProfile(
                email = email,
                firstName = "Ngomdé Cadet",
                lastName = "Kamdaou",
                title = "Android dev",
                profileImgUrl = "https://via.placeholder.com/400.png"
            )
        )
    }
}
