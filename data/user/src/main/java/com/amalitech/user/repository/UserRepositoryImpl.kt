package com.amalitech.user.repository

import com.amalitech.core.network.BookMeetingNetworkApi
import com.amalitech.user.data_source.local.UserDao
import com.amalitech.user.data_source.local.UsersEntity
import com.amalitech.user.data_source.local.cache.UserCache
import com.amalitech.user.data_source.remote.ApiUsersMapper
import com.amalitech.user.models.User
import com.amalitech.user.profile.model.Profile
import com.amalitech.user.profile.model.dto.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import retrofit2.HttpException

class UserRepositoryImpl(
    private val dao: UserDao,
    private val api: BookMeetingNetworkApi,
    private val cache: UserCache,
    private val apiUsersMapper: ApiUsersMapper
) : UserRepository {
    override suspend fun getRemoteUsers(): List<User> {
        try {
            val remoteUsers = api.getUsers().data
            return remoteUsers.map {
                apiUsersMapper.mapToDomain(
                    it
                )
            }
        } catch (exception: HttpException) {
            // throw NetworkException(exception.message ?: "Code ${exception.code()}")
            throw exception
        }
    }

    // TODO: alternatively, uncomment this to reformat message
    // class NetworkException(message: String) : Exception(message)
    override suspend fun getLocalUsers(): Flow<List<User>> {
        return cache.getUsers()
            .distinctUntilChanged()
            .map { users ->
                users.map { user ->
                    user.toDomain()
                }
            }
    }

    override suspend fun insertUsers(users: List<User>) {
        cache.storeUsers(
            users.map {
                UsersEntity.fromDomain(
                    it
                )
            }
        )
    }

    override suspend fun inviteUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(email: String): UserDto {
        return dao.getUser(email)
    }

    override suspend fun deleteUser(user: UserDto) {
        dao.deleteUser(user)
    }

    override suspend fun insertUser(user: UserDto) {
        dao.insertUser(user)
    }

    override suspend fun updateProfile(profile: Profile) {
        //TODO("Not yet implemented")
    }
//    override suspend fun updateProfile(profile: Profile) {
//        // TODO("Save in the API and use the profile"
//        //  "url to save into the local DB, use transactions")
//    }
}
