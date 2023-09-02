package com.amalitech.onboarding.login.use_case

import com.amalitech.core.util.ApiResult
import com.amalitech.onboarding.login.model.UserProfile
import com.amalitech.onboarding.repository.OnboardingRepository

class LogInUseCase(private val repos: OnboardingRepository) {
    /**
     * Log the user into their account by making an API call
     * using the provided credentials
     * @param password the user password
     * @param email the user email
     * @return an instance of UiText when there is an error returned
     * by the API, otherwise, null.
     */
    suspend operator fun invoke(email: String, password: String): ApiResult<UserProfile> {
        return repos.login(email, password)
    }
}
