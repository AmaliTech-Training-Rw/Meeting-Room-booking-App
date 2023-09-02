package com.amalitech.user.data_source.remote

import com.amalitech.core.data.model.mappers.ApiMapper
import com.amalitech.core.data.model.users.UserData
import com.amalitech.user.models.User

class ApiUsersMapper : ApiMapper<UserData, User> {
    override fun mapToDomain(apiEntity: UserData): User {
        return User(
            userId = apiEntity.userId,
            profilePic = "",
            username = apiEntity.username,
            email = apiEntity.email,
            isActive = false
        )
    }
}