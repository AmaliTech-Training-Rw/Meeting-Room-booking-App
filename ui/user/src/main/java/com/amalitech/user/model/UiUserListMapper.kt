package com.amalitech.user.model

import com.amalitech.core.data.model.mappers.UiMapper
import com.amalitech.user.models.User

class UiUserListMapper: UiMapper<User, UsersListUi> {

  override fun mapToView(input: User): UsersListUi {
    return UsersListUi(
        userId = input.userId,
        profilePic = input.profilePic,
        userName = input.userName,
        email = input.email,
        isActive = input.isActive
    )
  }

    // TODO: create a seperate domain user model to handle inviting of users, this helps with mapping
//    override fun mapToDomain(viewData: UsersListUi): User {
//        return User(
//            viewData.userId,
//            viewData.profilePic,
//
//        )
//    }
}
