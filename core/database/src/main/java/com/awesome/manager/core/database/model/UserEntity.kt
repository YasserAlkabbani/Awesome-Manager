package com.awesome.manager.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class UserEntity(
    @ColumnInfo(name = "user_id") @PrimaryKey  val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "updated_at") val createdAt: Long,
    @ColumnInfo(name = "created_at") val updatedAt: Long,
)