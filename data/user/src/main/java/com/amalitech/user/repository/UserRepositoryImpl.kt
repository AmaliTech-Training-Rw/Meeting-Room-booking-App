package com.amalitech.user.repository

import android.content.Context
import com.amalitech.core.data.repository.BaseRepo
import com.amalitech.core.domain.model.UserProfile
import com.amalitech.core.domain.preferences.OnboardingSharedPreferences
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.FileUtil
import com.amalitech.core.util.UiText
import com.amalitech.core.util.extractError
import com.amalitech.user.data_source.local.UserDao
import com.amalitech.user.data_source.remote.UserApiService
import com.amalitech.user.data_source.remote.UsersListDto
import com.amalitech.user.models.User
import com.amalitech.user.models.UserToAdd
import com.amalitech.user.profile.model.Profile
import com.amalitech.core.util.UserInfo
import com.amalitech.user.profile.model.dto.UserDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UserRepositoryImpl(
    private val dao: UserDao,
    private val api: UserApiService,
    private val sharedPreferences: OnboardingSharedPreferences
) : UserRepository, BaseRepo() {
    override suspend fun getUser(email: String): UserDto {
        return dao.getUser(email) ?: UserDto(-1, "", "", "", "", "")
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
                    val mapList = result.data?.data?.map { it.toUser() }
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

    override suspend fun updateProfile(profile: Profile, context: Context): ApiResult<UserProfile> {
        var imageFile: File? = null
        profile.profileImage?.let {
            imageFile = FileUtil.saveBitmapToFile(FileUtil.getFile(context, it))
        }
        var part: MultipartBody.Part? = null
        imageFile?.let {
            part = MultipartBody.Part.createFormData(
                name = "image",
                body = it.asRequestBody(),
                filename = it.name
            )
        }
        val result = safeApiCall(
            apiToBeCalled = {
                if (imageFile != null)
                    api.updateProfileWithImage(
                        firstName = profile.firstName,
                        lastName = profile.lastName,
                        title = profile.title,
                        currentPassword = profile.oldPassword,
                        password = profile.newPassword,
                        passwordConfirmation = profile.newPassword,
                        image = part
                    )
                else
                    api.updateProfileWithoutImage(
                        firstName = profile.firstName,
                        lastName = profile.lastName,
                        title = profile.title,
                        currentPassword = profile.oldPassword,
                        password = profile.newPassword,
                        passwordConfirmation = profile.newPassword,
                    )
            }, extractError = {
                extractError(it)
            }
        )
        return try {
            ApiResult(
                data = result.data?.data?.toProfileInfo(),
                error = result.error
            )
        } catch (e: Exception) {
            ApiResult(error = e.extractError())
        }
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

    override val userInfo: Flow<UserInfo> = flow {
        while (true) {
            val result = dao.getUser(sharedPreferences.loadLoggedInUserEmail())
            result?.let {
                emit(UserInfo("${result.firstName} ${result.lastName}", result.profileImgUrl))
            }
            delay(1000)
        }
    }
//    override suspend fun updateProfile(profile: Profile) {
//        // TODO("Save in the API and use the profile"
//        //  "url to save into the local DB, use transactions")
//    }
}
