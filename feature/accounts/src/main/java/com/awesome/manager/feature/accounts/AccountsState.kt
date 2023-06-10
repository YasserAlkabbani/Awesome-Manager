package com.awesome.manager.feature.accounts

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmCurrency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

private const val NAME:String="NAME"
private const val IMAGE_URL:String="IMAGE_URL"
private const val CURRENCY_ID:String="CURRENCY_ID"
private const val DEFAULT_TRANSACTION_TYPE:String="DEFAULT_TRANSACTION_TYPE"

class AccountsState(
    private val savedStateHandle: SavedStateHandle,
    private val returnCurrencyByCurrencyId:(String)->AmCurrency?,
    coroutineScope: CoroutineScope,
    amAccountAsFlow:Flow<AmAccount?>
){
    val amAccount=amAccountAsFlow.stateIn(scope = coroutineScope, started = SharingStarted.WhileSubscribed(5000),initialValue = null)

    val currency:StateFlow<AmCurrency?> = savedStateHandle.getStateFlow<String?>(CURRENCY_ID,"b9511023-aa62-4c73-9611-0f69ca252370").map {
        it?.let{returnCurrencyByCurrencyId(it)}
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(5000),null)
    fun updateCurrencyId(newValue:String)=savedStateHandle.set(CURRENCY_ID,newValue)

    val name:StateFlow<String> = savedStateHandle.getStateFlow(NAME,"")
    fun updateName(newValue:String)=savedStateHandle.set(NAME,newValue)

    val imageUrl:StateFlow<String> = savedStateHandle.getStateFlow(IMAGE_URL,"")
    fun updateImageUrl(newValue:String)=savedStateHandle.set(IMAGE_URL,newValue)

    val defaultTransactionType:StateFlow<String> = savedStateHandle.getStateFlow(DEFAULT_TRANSACTION_TYPE,"")
    fun updateDeafultTransactionType(newValue:Boolean)=savedStateHandle.set(DEFAULT_TRANSACTION_TYPE,newValue)

    private val _createAccountAction:MutableStateFlow<Unit?> =MutableStateFlow(null)
    val createAccountAction:StateFlow<Unit?> =_createAccountAction
    fun startCreateAccountAction(){_createAccountAction.update {  }}
    fun doneCreateAccountAction(){_createAccountAction.update { null }}
}