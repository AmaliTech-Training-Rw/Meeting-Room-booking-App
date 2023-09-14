package com.amalitech.onboarding_data.remote

import com.amalitech.onboarding_data.remote.dto.CreateOrganizationDto
import com.amalitech.onboarding_data.remote.dto.LoginDto
import com.amalitech.onboarding_data.remote.dto.OrganizationTypeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface OnboardingApiService {

    @POST("OrganisationUser/create")
    suspend fun createOrganization(
        @Query("username") username: String,
        @Query("organization_name") organizationName: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("confirm_password") confirmPassword: String,
        @Query("location_id") locationId: String,
        @Query("type_organisation_id") typeOrganisationId: String,
    ): Response<CreateOrganizationDto>

    @GET("typesOrganisation")
    suspend fun fetchOrganizations(): Response<OrganizationTypeDto>

    @POST("user/create")
    suspend fun createUser(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("password_confirmation") confirmPassword: String,
        @Header("Accept") type: String,
        @Header("Authorization") token: String,
    ): Response<CreateOrganizationDto>

    @POST("login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<LoginDto>
}
