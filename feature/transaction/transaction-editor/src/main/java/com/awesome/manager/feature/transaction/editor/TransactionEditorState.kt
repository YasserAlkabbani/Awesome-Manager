package com.awesome.manager.feature.transaction.editor

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.ui.ChipData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

private const val TITLE:String="TITLE"
private const val SUBTITLE:String="SUBTITLE"
private const val ACCOUNT_ID:String="ACCOUNT_ID"
private const val TRANSACTION_TYPE_ID="TRANSACTION_TYPE_ID"
private const val AMOUNT="AMOUNT"
private const val PAY_TRANSACTION="PAY_TRANSACTION"
private const val ACCOUNT_SEARCH_KEY="ACCOUNT_SEARCH_KEY"

class TransactionEditorState(
    private val savedStateHandle: SavedStateHandle,
    val transactionTypes:StateFlow<List<ChipData>>,
    val createTransaction:()->Unit,
    asAccountsSearchResult:StateFlow<String>.()->StateFlow<List<AmAccount>>,
    asAccount:StateFlow<String?>.()->StateFlow<AmAccount?>,
) {

    val title:StateFlow<String> =savedStateHandle.getStateFlow(TITLE,"")
    fun updateTitle(newTitle:String){ savedStateHandle[TITLE] = newTitle }

    val subtitle:StateFlow<String> =savedStateHandle.getStateFlow(SUBTITLE,"")
    fun updateSubTitle(newSubtitle:String){ savedStateHandle[SUBTITLE] = newSubtitle }

    val account:StateFlow<AmAccount?> =savedStateHandle.getStateFlow(ACCOUNT_ID,null).asAccount()
    fun updateAccountId(newAccountId:String){ savedStateHandle[ACCOUNT_ID] = newAccountId }

    val transactionType:StateFlow<String?> =savedStateHandle.getStateFlow(TRANSACTION_TYPE_ID,null)
    fun updateTransactionTypeId(newTransactionTypeId:String){ savedStateHandle[TRANSACTION_TYPE_ID] = newTransactionTypeId }

    val amount:StateFlow<Double> =savedStateHandle.getStateFlow(AMOUNT,0.0)
    fun updateAmount(newAmount:String){ savedStateHandle[AMOUNT] = newAmount.toDoubleOrNull()?:0.0}

    fun updateIsPayTransaction(newPay:Boolean){ savedStateHandle[PAY_TRANSACTION] = newPay }
    val payTransaction:StateFlow<Boolean> =savedStateHandle.getStateFlow(PAY_TRANSACTION,false)

    val accountSearchKey:StateFlow<String> =savedStateHandle.getStateFlow(ACCOUNT_SEARCH_KEY,"")
    val accountSearchResult:StateFlow<List<AmAccount>> =accountSearchKey.asAccountsSearchResult()
    fun updateAccountSearchKey(newSearchKey:String){savedStateHandle[ACCOUNT_SEARCH_KEY]=newSearchKey}

    private val _searchForAnAccount:MutableStateFlow<Boolean> =MutableStateFlow(false)
    val searchForAnAccount:StateFlow<Boolean> =_searchForAnAccount
    fun startSearchForAnAccount(){_searchForAnAccount.update { true }}
    fun doneSearchForAnAccount(){_searchForAnAccount.update { false }}

    private val _navigatePopup:MutableStateFlow<Unit?> =MutableStateFlow(null)
    val navigatePopup:StateFlow<Unit?> =_navigatePopup
    fun startPoup(){_navigatePopup.update {  }}
    fun donePoup(){_navigatePopup.update { null }}

}