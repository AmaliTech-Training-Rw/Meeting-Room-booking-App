package com.amalitech.onboarding.login.use_case

import com.amalitech.core.util.Response
import com.amalitech.onboarding.login.model.UserProfile
import kotlinx.coroutines.delay

class LoadProfileInformationUseCase {
    suspend operator fun invoke(email: String): Response<UserProfile> {
        delay(2000)
        return Response(
            data = UserProfile(
                email = email,
                firstName = "John",
                lastName = "Doe",
                title = "Android dev",
                profileImgUrl = "https://via.eholder.com/400.png",
                id = 2,
                isAdmin = 1,
                locationId = 2,
                organisationId = 2,
                userId = 1,
                username = ""
            )
        )
    }
}
