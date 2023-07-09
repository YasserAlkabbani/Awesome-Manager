package com.awesome.manager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.awesome.manager.core.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertUser(userEntity: UserEntity)

    @Query("SELECT * FROM users")
    fun returnUser():Flow<UserEntity>

}