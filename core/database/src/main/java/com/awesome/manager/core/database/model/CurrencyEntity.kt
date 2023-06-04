package com.awesome.manager.core.database.model

import androidx.room.Entity


@Entity(tableName = "currencies")
data class CurrencyEntity(
    val id:String,
    val name:String,
    val image_url:String,
    val default_currency_id:String,
    val default_transactions_type:String,
    val creator_user_id:String,
    val created_at:String,
    val updated_at:String,
)