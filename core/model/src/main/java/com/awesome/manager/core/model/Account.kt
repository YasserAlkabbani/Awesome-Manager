package com.awesome.manager.core.model

data class AmAccount (
    val id:String,
    val name:String,
    val imageUrl:String,
    val currency: AmCurrency,
    val createdDate:Long,
    val shouldReturnValueByDefault:Boolean
)