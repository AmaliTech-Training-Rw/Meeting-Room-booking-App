package com.amalitech.user.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amalitech.user.model.dto.UserDto

@Dao
interface UserDao {
    @Query("SELECT * FROM USERDTO WHERE uid = :id")
    suspend fun getUser(id: String): UserDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userDto: UserDto)

    @Delete
    suspend fun deleteUser(userDto: UserDto)
}
