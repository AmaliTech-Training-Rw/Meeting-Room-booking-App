package com.amalitech.user.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amalitech.user.profile.model.dto.UserDto

@Database(entities = [UserDto::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "UserDatabase"
    }
}
