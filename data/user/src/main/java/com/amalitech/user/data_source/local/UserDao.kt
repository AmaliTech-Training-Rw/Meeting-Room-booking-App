package com.amalitech.user.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amalitech.user.profile.model.dto.UserDto

@Dao
interface UserDao {
    @Query("SELECT * FROM USERDTO WHERE email = :email")
    suspend fun getUser(email: String): UserDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userDto: UserDto)

    @Delete
    suspend fun deleteUser(userDto: UserDto)
}
