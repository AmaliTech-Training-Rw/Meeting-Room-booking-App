package com.amalitech.user.data_source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amalitech.user.models.User

@Entity(
    tableName = "users",
)
data class UsersEntity(
    @PrimaryKey
    val userId: Int,
    val profilePic: String,
    val userName: String,
    val email: String,
    val isActive: Boolean
) {
    companion object {
        fun fromDomain(user: User): UsersEntity {
            return UsersEntity(
                userId = user.userId,
                profilePic = user.profilePic,
                userName = user.username,
                email = user.email,
                isActive = user.isActive,
            )
        }
    }

    fun toDomain(): User = User(
        userId,
        "",
        userName,
        email,
        isActive
    )
}