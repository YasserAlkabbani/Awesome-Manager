package com.awesome.manager.core.model

data class AmUser(
    val id:String,
    val name:String,
    val email:String,
    val imageUrl:String?,
    val createdAt:String,
    val updatedAt:String,
)