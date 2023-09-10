package com.amalitech.onboarding_data.repository

import com.amalitech.core.data.repository.BaseRepo
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.onboarding.login.model.UserProfile
import com.amalitech.onboarding.repository.OnboardingRepository
import com.amalitech.onboarding.signup.model.CreateOrganization
import com.amalitech.onboarding.signup.model.OrganizationType
import com.amalitech.onboarding.signup.model.User
import com.amalitech.onboarding_data.remote.OnboardingApiService
import com.amalitech.onboarding_data.remote.dto.toProfileInfo

class OnboardingRepositoryImpl(
    private val api: OnboardingApiService
) : OnboardingRepository, BaseRepo() {
    override suspend fun createOrganization(user: User): ApiResult<CreateOrganization> {
        val apiResult = safeApiCall(
            apiToBeCalled = {
                api.createOrganization(
                    username = user.username,
                    organizationName = user.organizationName,
                    email = user.email,
                    password = user.password,
                    confirmPassword = user.passwordConfirmation,
                    locationId = user.location.toString(),
                    typeOrganisationId = user.typeOfOrganization.toString()
                )
            }, extractError = { jsonObject ->
                extractError(jsonObject)
            }
        )
        return try {
            ApiResult(data = apiResult.data?.toCreateOrganization(), error = apiResult.error)
        } catch (e: Exception) {
            val localizedMessage = e.localizedMessage
            if (localizedMessage != null)
                ApiResult(error = UiText.DynamicString(localizedMessage))
            else ApiResult(error = UiText.StringResource(com.amalitech.core.R.string.error_default_message))
        }
    }

    override suspend fun fetchOrganizationType(): ApiResult<OrganizationType> {
        val apiResult = safeApiCall(
            apiToBeCalled = {
                api.fetchOrganizations()
            },
            extractError = {
                return@safeApiCall UiText.StringResource(com.amalitech.core.R.string.error_default_message)
            }
        )
        return try {
            ApiResult(data = apiResult.data?.toOrganizationType(), error = apiResult.error)
        } catch (e: Exception) {
            val localizedMessage = e.localizedMessage
            if (localizedMessage != null)
                ApiResult(error = UiText.DynamicString(localizedMessage))
            else ApiResult(error = UiText.StringResource(com.amalitech.core.R.string.error_default_message))
        }
    }

    override suspend fun createUser(
        username: String,
        password: String,
        passwordConfirmation: String,
        token: String
    ): ApiResult<CreateOrganization> {
        val apiResult = safeApiCall(
            apiToBeCalled = {
                api.createUser(
                    username,
                    password,
                    passwordConfirmation,
                    token
                )
            }, extractError = { jsonObject ->
                extractError(jsonObject)
            }
        )
        return try {
            ApiResult(data = apiResult.data?.toCreateOrganization(), error = apiResult.error)
        } catch (e: Exception) {
            val localizedMessage = e.localizedMessage
            if (localizedMessage != null)
                ApiResult(error = UiText.DynamicString(localizedMessage))
            else ApiResult(error = UiText.StringResource(com.amalitech.core.R.string.error_default_message))
        }
    }

    override suspend fun login(email: String, password: String): ApiResult<UserProfile> {
        val apiResult = safeApiCall(
            apiToBeCalled = {
                api.login(email, password)
            },
            extractError = {
                extractError(it)
            }
        )
        return try {
            ApiResult(data = apiResult.data?.data?.toProfileInfo(token = apiResult.data?.token?:""), error = apiResult.error)
        } catch (e: Exception) {
            val localizedMessage = e.localizedMessage
            if (localizedMessage != null)
                ApiResult(error = UiText.DynamicString(localizedMessage))
            else ApiResult(error = UiText.StringResource(com.amalitech.core.R.string.error_default_message))
        }
    }
}
