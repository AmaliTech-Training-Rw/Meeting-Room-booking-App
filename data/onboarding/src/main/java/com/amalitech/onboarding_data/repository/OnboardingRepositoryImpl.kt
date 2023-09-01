package com.amalitech.onboarding_data.repository

import com.amalitech.core.data.repository.BaseRepo
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.onboarding.repository.OnboardingRepository
import com.amalitech.onboarding.signup.model.CreateOrganization
import com.amalitech.onboarding.signup.model.LocationX
import com.amalitech.onboarding.signup.model.OrganizationType
import com.amalitech.onboarding.signup.model.User
import com.amalitech.onboarding_data.remote.OnboardingApiService

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
        return ApiResult(data = apiResult.data?.toCreateOrganization(), error = apiResult.error)
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
        return ApiResult(data = apiResult.data?.toOrganizationType(), error = apiResult.error)
    }

    override suspend fun fetchLocations(): ApiResult<List<LocationX>> {
        val apiResult = safeApiCall(
            apiToBeCalled = {
                api.fetchLocations()
            },
            extractError = {
                return@safeApiCall UiText.StringResource(com.amalitech.core.R.string.error_default_message)
            }
        )
        return ApiResult(
            data = apiResult.data?.locations,
            error = apiResult.error
        )
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
        return ApiResult(data = apiResult.data?.toCreateOrganization(), error = apiResult.error)
    }

    override suspend fun login(email: String, password: String): ApiResult<Boolean> {
        TODO("Not yet implemented")
    }
}
