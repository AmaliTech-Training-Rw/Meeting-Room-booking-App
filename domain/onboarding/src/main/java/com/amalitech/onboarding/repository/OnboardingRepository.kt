package com.amalitech.onboarding.repository

import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.onboarding.signup.model.CreateOrganization
import com.amalitech.onboarding.signup.model.OrganizationType
import com.amalitech.onboarding.login.model.UserProfile
import com.amalitech.onboarding.signup.model.User

interface OnboardingRepository {
    suspend fun createOrganization(user: User): ApiResult<CreateOrganization>
    suspend fun fetchOrganizationType(): ApiResult<OrganizationType>
    suspend fun createUser(
        username: String,
        password: String,
        passwordConfirmation: String,
        token: String,
    ): ApiResult<CreateOrganization>

    suspend fun login(email: String, password: String): ApiResult<UserProfile>

    suspend fun resetPassword(password: String, passwordConfirmation: String, token: String): UiText?
    suspend fun askResetLink(email: String): UiText?
}
