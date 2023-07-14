package com.amalitech.user.profile.model.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDto(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    @ColumnInfo("first_name") val firstName: String,
    @ColumnInfo("last_name") val lastName: String,
    val email: String,
    val title: String,
    @ColumnInfo("profile_img_url") val profileImgUrl: String
)
