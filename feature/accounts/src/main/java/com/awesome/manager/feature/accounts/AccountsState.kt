package com.awesome.manager.feature.accounts

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.model.AmCurrency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

private const val NAME:String="NAME"
private const val IMAGE_URL:String="IMAGE_URL"
private const val CURRENCY_ID:String="CURRENCY_ID"
private const val CREATED_DATE:String="CREATED_DATE"
private const val SHOULD_RETURN_VALUE_BY_DEFAULT:String="SHOULD_RETURN_VALUE_BY_DEFAULT"

class AccountsState(
    private val savedStateHandle: SavedStateHandle,
    private val returnCurrencyByCurrencyId:(String)->AmCurrency?,
    private val coroutineScope: CoroutineScope
){

    private val _createAccount:MutableStateFlow<Unit?> =MutableStateFlow(null)
    val createAccount:StateFlow<Unit?> =_createAccount
    fun startCreateAccount(){_createAccount.update {  }}
    fun doneCreateAccount(){_createAccount.update { null }}

    fun updateAccount(){

    }
    val name:StateFlow<String> = savedStateHandle.getStateFlow(NAME,"")
    fun updateName(newValue:String)=savedStateHandle.set(NAME,newValue)

    val imageUrl:StateFlow<String> = savedStateHandle.getStateFlow(IMAGE_URL,"")
    fun updateImageUrl(newValue:String)=savedStateHandle.set(IMAGE_URL,newValue)

    val currency:StateFlow<AmCurrency?> = savedStateHandle.getStateFlow(CURRENCY_ID,"").map {
        returnCurrencyByCurrencyId(it)
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(5000),null)
    fun updateCurrencyId(newValue:String)=savedStateHandle.set(CURRENCY_ID,newValue)

    val createdDate:StateFlow<Long> = savedStateHandle.getStateFlow(CREATED_DATE,System.currentTimeMillis())
    fun updateCreatedDate(newValue:Long)=savedStateHandle.set(CREATED_DATE,newValue)

    val shouldReturnValueByDefault:StateFlow<String> = savedStateHandle.getStateFlow(SHOULD_RETURN_VALUE_BY_DEFAULT,"")
    fun updateShouldReturnValueByDefault(newValue:Boolean)=savedStateHandle.set(SHOULD_RETURN_VALUE_BY_DEFAULT,newValue)

}