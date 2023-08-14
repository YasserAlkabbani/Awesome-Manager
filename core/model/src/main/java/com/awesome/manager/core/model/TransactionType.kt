package com.awesome.manager.core.model

data class AmTransactionType(
    val id: String,
    val title:String,
    val deadTransaction:Boolean,
    val defaultPaymentTransaction:Boolean,
    val switchPaymentTransaction:Boolean,
    val createdAt:String,
    val updatedAt:String
){
    fun switchDirection(newDir:Boolean?)=
        if (!switchPaymentTransaction||newDir==defaultPaymentTransaction||newDir==null) this else this.copy(defaultPaymentTransaction = newDir)
}