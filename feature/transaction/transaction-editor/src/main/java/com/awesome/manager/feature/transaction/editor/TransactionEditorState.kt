package com.awesome.manager.feature.transaction.editor

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.common.states.setData
import com.awesome.manager.core.common.states.updateData
import com.awesome.manager.core.designsystem.ui_actions.MainActionsState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class TransactionEditorState(
    val transactionTypes: StateFlow<List<AmTransactionType>>,
    val accountsSearchResults: StateFlow<String>.()->StateFlow<List<AmAccount>>,
    val createTransaction: () -> Unit,
) : MainActionsState() {

    private val _transactionEditorData: MutableStateFlow<DataState<TransactionEditorData>> =
        MutableStateFlow(DataState.Loading)
    val transactionEditorData: StateFlow<DataState<TransactionEditorData>> = _transactionEditorData
    fun asCreateTransaction(creatorUserId: String) {
        _transactionEditorData.setData {
            TransactionEditorData.TransactionEditorCreate(creatorUserId = creatorUserId)
        }
    }

    private val _searchKey:MutableStateFlow<String> = MutableStateFlow("")
    val accountsList:StateFlow<List<AmAccount>> = _searchKey.accountsSearchResults()
    fun updateSearchKey(newSearchKey:String){_searchKey.update { newSearchKey }}

    private val _searchForAnAccountBottomSheet:MutableStateFlow<Boolean> = MutableStateFlow(false)
    val searchForAnAccountBottomSheet:MutableStateFlow<Boolean> = _searchForAnAccountBottomSheet
    fun requestSearchForAnAccountBottomSheet(){_searchForAnAccountBottomSheet.update { true }}
    fun doneSearchForAnAccountBottomSheet(){_searchForAnAccountBottomSheet.update { false }}

    fun asEditTransaction(transaction: AmTransaction,account: AmAccount) {
        _transactionEditorData.setData {
            TransactionEditorData.TransactionEditorCreate(
                id = transaction.id,
                creatorUserId = transaction.creatorUserId,
                title = transaction.title,
                subtitle = transaction.subtitle,
                selectedAccount = account,
                selectedTransactionType = transaction.transactionType,
                paymentTransaction = transaction.paymentTransaction,
                amount = transaction.amount,
            )
        }
    }

    fun updateTitle(title: String) =
        _transactionEditorData.updateData { it.updateTitle(title) }

    fun updateSubTitle(subtitle: String) =
        _transactionEditorData.updateData { it.updateSubTitle(subtitle) }

    fun updateAmount(amount: String) =
        _transactionEditorData.updateData {
            it.updateAmount(amount.toDoubleOrNull()?:0.0)
        }

    fun selectAccount(account: AmAccount) {
        _transactionEditorData.updateData { it.selectAccount(account) }
        selectTransactionType(account.defaultTransactionType)
        dismissBottomSheet()
    }

    fun selectTransactionType(transactionType: AmTransactionType) =
        _transactionEditorData.updateData { it.selectTransactionType(transactionType) }

    fun setAsPay() =
        _transactionEditorData.updateData { it.setAsPay() }

    fun setAsReceive() =
        _transactionEditorData.updateData { it.setAsReceive() }


    fun validateTransaction(): UpsertTransaction? =
        (transactionEditorData.value as? DataState.Success)?.data?.validateTransactionData()

}

sealed class TransactionEditorData {

    abstract val id: String
    abstract val creatorUserId: String
    abstract val title: String
    abstract val subtitle: String
    abstract val selectedAccount: AmAccount?
    abstract val selectedTransactionType: AmTransactionType?
    abstract val paymentTransaction: Boolean
    abstract val amount: Double

    abstract fun updateTitle(newTitle: String): TransactionEditorData
    abstract fun updateSubTitle(newSubtitle: String): TransactionEditorData
    abstract fun updateAmount(newAmount:Double):TransactionEditorData
    abstract fun selectAccount(newAccount: AmAccount): TransactionEditorData
    abstract fun selectTransactionType(newTransactionType: AmTransactionType): TransactionEditorData
    abstract fun setAsPay(): TransactionEditorData
    abstract fun setAsReceive(): TransactionEditorData
    abstract fun validateTransactionData(): UpsertTransaction?

    data class TransactionEditorCreate(
        override val id: String = UUID.randomUUID().toString(),
        override val creatorUserId: String,
        override val title: String = "",
        override val subtitle: String = "",
        override val selectedAccount: AmAccount? = null,
        override val selectedTransactionType: AmTransactionType? = null,
        override val paymentTransaction: Boolean = false,
        override val amount: Double = 0.0
    ) : TransactionEditorData() {
        override fun updateTitle(newTitle: String): TransactionEditorData = copy(title = newTitle)

        override fun updateSubTitle(newSubtitle: String): TransactionEditorData =
            copy(subtitle = newSubtitle)

        override fun updateAmount(newAmount: Double): TransactionEditorData =
            copy(amount=newAmount)

        override fun selectAccount(newAccount: AmAccount): TransactionEditorData =
            copy(selectedAccount = newAccount)

        override fun selectTransactionType(newTransactionType: AmTransactionType): TransactionEditorData =
            copy(selectedTransactionType = newTransactionType)

        override fun setAsPay(): TransactionEditorData = copy(paymentTransaction = true)

        override fun setAsReceive(): TransactionEditorData = copy(paymentTransaction = false)
        override fun validateTransactionData(): UpsertTransaction? =
            if (title.isNotEmpty() && selectedAccount != null && selectedTransactionType != null) {
                UpsertTransaction(
                    id = id, creatorUserId = creatorUserId, title = title, subtitle = subtitle,
                    accountId = selectedAccount.id, amount = amount,
                    transactionTypeId = selectedTransactionType.id,
                    paymentTransaction = paymentTransaction
                )
            } else null
    }

    data class TransactionEditorUpdate(
        override val id: String,
        override val creatorUserId: String,
        override val title: String,
        override val subtitle: String,
        override val selectedAccount: AmAccount,
        override val selectedTransactionType: AmTransactionType,
        override val paymentTransaction: Boolean,
        override val amount: Double
    ) : TransactionEditorData() {
        override fun updateTitle(newTitle: String): TransactionEditorData = copy(title = newTitle)

        override fun updateSubTitle(newSubtitle: String): TransactionEditorData =
            copy(subtitle = newSubtitle)
        override fun updateAmount(newAmount: Double): TransactionEditorData =
            copy(amount=amount)
        override fun selectAccount(newAccount: AmAccount): TransactionEditorData =
            copy(selectedAccount = newAccount)

        override fun selectTransactionType(newTransactionType: AmTransactionType): TransactionEditorData =
            copy(selectedTransactionType = newTransactionType)

        override fun setAsPay(): TransactionEditorData = copy(paymentTransaction = true)

        override fun setAsReceive(): TransactionEditorData = copy(paymentTransaction = false)
        override fun validateTransactionData(): UpsertTransaction? = if (title.isNotEmpty()) {
            UpsertTransaction(
                id = id, creatorUserId = creatorUserId, title = title, subtitle = subtitle,
                accountId = selectedAccount.id, amount = amount,
                transactionTypeId = selectedTransactionType.id,
                paymentTransaction = paymentTransaction
            )
        } else null
    }

}