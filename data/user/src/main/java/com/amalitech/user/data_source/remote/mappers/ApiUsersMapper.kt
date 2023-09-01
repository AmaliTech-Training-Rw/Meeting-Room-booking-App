package com.amalitech.user.data_source.remote.mappers

import com.amalitech.core.data.model.mappers.ApiMapper
import com.amalitech.core.data.model.users.Users
import com.amalitech.user.models.User

class ApiUsersMapper : ApiMapper<Users, User> {
    override fun mapToDomain(apiEntity: Users): User {
        return User(
            userId = apiEntity.userId,
            profilePic = "",
            userName = apiEntity.username,
            firstName = apiEntity.firstName,
            lastName = apiEntity.lastName,
            email = apiEntity.email,
            isActive = false
        )
    }
}