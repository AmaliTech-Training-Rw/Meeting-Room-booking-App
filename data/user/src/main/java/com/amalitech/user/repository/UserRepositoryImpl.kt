package com.amalitech.user.repository

import com.amalitech.core.data.repository.BaseRepo
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.user.data_source.local.UserDao
import com.amalitech.user.data_source.remote.UserApiService
import com.amalitech.user.data_source.remote.UsersListDto
import com.amalitech.user.models.User
import com.amalitech.user.models.UserToAdd
import com.amalitech.user.profile.model.Profile
import com.amalitech.user.profile.model.dto.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val dao: UserDao,
    private val api: UserApiService
) : UserRepository, BaseRepo() {
    override suspend fun getUser(email: String): UserDto {
        return dao.getUser(email)
    }

    override suspend fun deleteUser(user: UserDto) {
        dao.deleteUser(user)
    }

    override suspend fun insertUser(user: UserDto) {
        dao.insertUser(user)
    }

    override suspend fun getUsers(): ApiResult<Flow<List<User>>> {

        return try {
            var result: ApiResult<UsersListDto> = ApiResult()
            val flow = flow {
                while (true) {
                    result = safeApiCall(
                        apiToBeCalled = {
                            api.fetchAllUsers()
                        },
                        extractError = {
                            extractError(it)
                        }
                    )
                    emit(result.data?.data?.map { it.toUser() } ?: emptyList())
                }
            }
            ApiResult(
                data = flow,
                error = result.error
            )
        } catch (e: Exception) {
            val localizedMessage = e.localizedMessage
            if (localizedMessage != null)
                ApiResult(error = UiText.DynamicString(localizedMessage))
            else
                ApiResult(error = UiText.StringResource(com.amalitech.core.R.string.error_default_message))
        }
    }

    override suspend fun addUser(user: UserToAdd): UiText? {
        val apiResult = safeApiCall(
            apiToBeCalled = {
                api.addUser(
                    user.firstName,
                    user.lastName,
                    user.email,
                    user.locationId
                )
            },
            extractError = {
                extractError(it)
            }
        )
        return apiResult.error
    }

    override suspend fun updateProfile(profile: Profile) {
        //TODO("Not yet implemented")
    }

    override suspend fun logout(token: String) {
        safeApiCall(
            apiToBeCalled = {
                api.logout()
            },
            extractError = {
                extractError(it)
            }
        )
    }
//    override suspend fun updateProfile(profile: Profile) {
//        // TODO("Save in the API and use the profile"
//        //  "url to save into the local DB, use transactions")
//    }
}
