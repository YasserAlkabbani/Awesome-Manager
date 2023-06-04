package com.awesome.manager.core.model

data class AmTransaction(
    val id: String,
    val accountId:String,
    val creatorId:String,
    val title:String,
    val subTitle:String,
    val createdDate:Long,
    val incomingAmount:Double,
    val outgoingAmount:Double,
    val shouldReturn:Boolean
)