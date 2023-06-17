package com.awesome.manager.feature.account.details

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmCurrency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

private const val NAME:String="NAME"
private const val IMAGE_URL:String="IMAGE_URL"
private const val CURRENCY_ID:String="CURRENCY_ID"
private const val DEFAULT_TRANSACTION_TYPE:String="DEFAULT_TRANSACTION_TYPE"

class AccountDetailsState(
    private val savedStateHandle: SavedStateHandle,
    private val defaultCurrency:AmCurrency,
    private val returnCurrencyByCurrencyId:(String)-> AmCurrency,
    coroutineScope: CoroutineScope,
    amAccountAsFlow: Flow<AmAccount?>,
    amAccountsAsFlow: Flow<List<AmAccount>>
){
    val currency: StateFlow<AmCurrency> = savedStateHandle.getStateFlow(CURRENCY_ID,defaultCurrency.id).map {
        returnCurrencyByCurrencyId(it)
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(5000),defaultCurrency)
    fun updateCurrencyId(newValue:String)=savedStateHandle.set(CURRENCY_ID,newValue)

    val name: StateFlow<String> = savedStateHandle.getStateFlow(NAME,"")
    fun updateName(newValue:String)=savedStateHandle.set(NAME,newValue)

    val imageUrl: StateFlow<String> = savedStateHandle.getStateFlow(IMAGE_URL,"")
    fun updateImageUrl(newValue:String)=savedStateHandle.set(IMAGE_URL,newValue)

    val defaultTransactionType: StateFlow<String> = savedStateHandle.getStateFlow(
        DEFAULT_TRANSACTION_TYPE,"")
    fun updateDeafultTransactionType(newValue:Boolean)=savedStateHandle.set(DEFAULT_TRANSACTION_TYPE,newValue)

}