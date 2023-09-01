package com.amalitech.user.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.amalitech.user.models.User
import com.amalitech.user.profile.model.dto.UserDto
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY userId DESC")
    fun getAllUsers(): Flow<List<UsersEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UsersEntity>)

    @Query("SELECT * FROM USERDTO WHERE email = :email")
    suspend fun getUser(email: String): UserDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userDto: UserDto)

    @Delete
    suspend fun deleteUser(userDto: UserDto)
}
