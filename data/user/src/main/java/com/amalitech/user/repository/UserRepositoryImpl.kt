package com.amalitech.user.repository

import com.amalitech.core.data.repository.BaseRepo
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.core.util.extractError
import com.amalitech.user.data_source.local.UserDao
import com.amalitech.user.data_source.remote.UserApiService
import com.amalitech.user.data_source.remote.UsersListDto
import com.amalitech.user.models.User
import com.amalitech.user.models.UserToAdd
import com.amalitech.user.profile.model.Profile
import com.amalitech.user.profile.model.dto.UserDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val dao: UserDao,
    private val api: UserApiService
) : UserRepository, BaseRepo() {
    override suspend fun getUser(email: String): UserDto {
        return dao.getUser(email)
    }

    override suspend fun deleteUser(userId: String): UiText? {
        val apiResult = safeApiCall(
            apiToBeCalled = {
                api.deleteUser(userId.toIntOrNull() ?: -1)
            },
            extractError = {
                extractError(it)
            }
        )
        return apiResult.error
    }

    override suspend fun saveLoggedInUser(user: UserDto) {
        try {
            dao.saveLoggedInUser(user)

        } catch (e: Exception) {
            e.extractError()
        }
    }

    override suspend fun getUsers(isInviting: Boolean): ApiResult<Flow<List<User>>> {
        return try {
            var result: ApiResult<UsersListDto> = ApiResult()
            val flow = flow {
                while (!isInviting) {
                    result = safeApiCall(
                        apiToBeCalled = {
                            api.fetchAllUsers()
                        },
                        extractError = {
                            extractError(it)
                        }
                    )
                    val mapList= result.data?.data?.map { it.toUser() }
                    mapList?.let {
                        emit(it)
                    }
                    delay(5000)
                }
            }
            ApiResult(
                data = flow,
                error = result.error
            )
        } catch (e: Exception) {
            return ApiResult(error = e.extractError())
        }
    }

    override suspend fun addUser(user: UserToAdd): UiText? {
        val apiResult = safeApiCall(
            apiToBeCalled = {
                api.addUser(
                    user.firstName,
                    user.lastName,
                    user.email,
                    user.locationId,
                    user.isAdmin
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
