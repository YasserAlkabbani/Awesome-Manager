package com.awesome.manager.feature.transaction.editor

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

private const val TITLE: String = "TITLE"
private const val SUBTITLE: String = "SUBTITLE"
private const val ACCOUNT_ID: String = "ACCOUNT_ID"
private const val TRANSACTION_TYPE_ID = "TRANSACTION_TYPE_ID"
private const val PAYMENT_TRANSACTION = "PAYMENT_TRANSACTION"
private const val AMOUNT = "AMOUNT"
private const val ACCOUNT_SEARCH_KEY = "ACCOUNT_SEARCH_KEY"

class TransactionEditorState(
    private val savedStateHandle: SavedStateHandle,
    val transactionTypes: StateFlow<List<AmTransactionType>>,
    val createTransaction: () -> Unit,
    asAccountsSearchResult: StateFlow<String>.() -> StateFlow<List<AmAccount>>,
    asAccount: StateFlow<String?>.() -> StateFlow<AmAccount?>,
    asTransactionType: StateFlow<String?>.() -> StateFlow<AmTransactionType?>,
) {

    val title: StateFlow<String> = savedStateHandle.getStateFlow(TITLE, "")
    fun updateTitle(newTitle: String) {
        savedStateHandle[TITLE] = newTitle
    }

    val subtitle: StateFlow<String> = savedStateHandle.getStateFlow(SUBTITLE, "")
    fun updateSubTitle(newSubtitle: String) {
        savedStateHandle[SUBTITLE] = newSubtitle
    }

    val selectedAccount: StateFlow<AmAccount?> =
        savedStateHandle.getStateFlow(ACCOUNT_ID, null).asAccount()

    private fun updateAccountId(newAccountId: String) {
        savedStateHandle[ACCOUNT_ID] = newAccountId
    }

    val selectedTransactionType: StateFlow<AmTransactionType?> =
        savedStateHandle.getStateFlow(TRANSACTION_TYPE_ID, null).asTransactionType()

    fun updateTransactionTypeId(newTransactionTypeId: String) {
        savedStateHandle[TRANSACTION_TYPE_ID] = newTransactionTypeId
    }

    val paymentTransaction: StateFlow<Boolean> =
        savedStateHandle.getStateFlow(PAYMENT_TRANSACTION, false)

    fun updateTransactionPayment(payment: Boolean) {
        savedStateHandle[PAYMENT_TRANSACTION] = payment
    }

    val amount: StateFlow<String> = savedStateHandle.getStateFlow(AMOUNT, "0")
    fun updateAmount(newAmount: String) {
        val lastAmount = newAmount
            .let { if (newAmount.count { it == '.' } < 2) it else amount.value }
            .filter { it.isDigit() || it == '.' }
            .let {
                val splitNumber = it.split('.')
                val newBefore = splitNumber.getOrElse(0) { "" }.ifBlank { "0" }.take(20)
                val newAfter = splitNumber.getOrNull(1)?.take(3)?.let { ".".plus(it) }.orEmpty()
                "$newBefore$newAfter"
            }
        savedStateHandle[AMOUNT] = lastAmount
    }

    fun selectAccount(selectedAccount: AmAccount) {
        updateAccountId(selectedAccount.id)
        updateTransactionTypeId(selectedAccount.defaultTransactionType.id)
        doneSearchForAnAccount()
    }

    val accountSearchKey: StateFlow<String> = savedStateHandle.getStateFlow(ACCOUNT_SEARCH_KEY, "")
    val accountSearchResult: StateFlow<List<AmAccount>> = accountSearchKey.asAccountsSearchResult()
    fun updateAccountSearchKey(newSearchKey: String) {
        savedStateHandle[ACCOUNT_SEARCH_KEY] = newSearchKey
    }

    private val _searchForAnAccountBottomSheet: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val searchForAnAccountBottomSheet: StateFlow<Boolean> = _searchForAnAccountBottomSheet
    fun startSearchForAnAccount() {
        _searchForAnAccountBottomSheet.update { true }
    }

    fun doneSearchForAnAccount() {
        _searchForAnAccountBottomSheet.update { false }
    }

    fun fillTransactionData(amTransaction: AmTransaction) {
        updateTitle(amTransaction.title)
        updateSubTitle(amTransaction.subtitle)
        updateAccountId(amTransaction.accountId)
        updateTransactionTypeId(amTransaction.transactionType.id)
        updateTransactionPayment(amTransaction.paymentTransaction)
        updateAmount(amTransaction.amount.toString())
    }

    fun validateTransaction(transactionId: String, creatorUserId: String?): UpsertTransaction? =
        if (
            !creatorUserId.isNullOrBlank() &&
            selectedAccount.value != null &&
            selectedTransactionType.value != null
        ) {
            UpsertTransaction(
                id = transactionId,
                accountId = selectedAccount.value!!.id,
                creatorUserId = creatorUserId,
                title = title.value,
                subtitle = subtitle.value,
                amount = amount.value.toDoubleOrNull() ?: 0.0,
                transactionTypeId = selectedTransactionType.value!!.id,
                paymentTransaction = paymentTransaction.value,
            )
        } else null

    private val _navigatePopup: MutableStateFlow<Unit?> = MutableStateFlow(null)
    val navigatePopup: StateFlow<Unit?> = _navigatePopup
    fun startPop() {
        _navigatePopup.update { }
    }

    fun donePop() {
        _navigatePopup.update { null }
    }

}