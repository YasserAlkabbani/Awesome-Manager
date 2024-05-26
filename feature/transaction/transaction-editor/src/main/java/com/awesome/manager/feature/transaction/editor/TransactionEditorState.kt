package com.awesome.manager.feature.transaction.editor

import androidx.paging.PagingData
import com.awesome.manager.core.common.extentions.asDate
import com.awesome.manager.core.common.extentions.currentTime
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.common.states.setData
import com.awesome.manager.core.common.states.updateData
import com.awesome.manager.core.designsystem.ui_actions.main.MainState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class TransactionEditorState(
    val accountsSearchResults: StateFlow<String>.() -> Flow<PagingData<AmAccount>>,
    val createTransaction: () -> Unit,
) : MainState() {

    val transactionTypes = AmTransactionType.entries.toList()

    private val _transactionEditorInput: MutableStateFlow<DataState<TransactionEditorInput>> =
        MutableStateFlow(DataState.Loading)
    val transactionEditorInput: StateFlow<DataState<TransactionEditorInput>> =
        _transactionEditorInput

    fun asCreateTransaction(creatorUserId: String) {
        _transactionEditorInput.setData {
            TransactionEditorInput.TransactionEditorCreate(creatorUserId = creatorUserId)
        }
    }

    private val _searchKey: MutableStateFlow<String> = MutableStateFlow("")
    val accountsList: Flow<PagingData<AmAccount>> = _searchKey.accountsSearchResults()
    fun updateSearchKey(newSearchKey: String) = _searchKey.update { newSearchKey }


    private val _searchForAnAccountBottomSheet: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val searchForAnAccountBottomSheet: MutableStateFlow<Boolean> = _searchForAnAccountBottomSheet
    fun requestSearchForAnAccountBottomSheet() =
        _searchForAnAccountBottomSheet.update { true }

    fun doneSearchForAnAccountBottomSheet() =
        _searchForAnAccountBottomSheet.update { false }

    fun asEditTransaction(transaction: AmTransaction, account: AmAccount) {
        _transactionEditorInput.setData {
            TransactionEditorInput.TransactionEditorUpdate(
                id = transaction.id,
                creatorUserId = transaction.creatorUserId,
                title = transaction.title,
                subtitle = transaction.subtitle,
                selectedAccount = account,
                selectedTransactionType = transaction.transactionType,
                amount = transaction.amount,
                transactionAt = transaction.transactionAt
            )
        }
    }

    fun updateTitle(title: String) =
        _transactionEditorInput.updateData { it.updateTitle(title) }

    fun updateSubTitle(subtitle: String) =
        _transactionEditorInput.updateData { it.updateSubTitle(subtitle) }

    fun updateAmount(amount: String) =
        _transactionEditorInput.updateData {
            it.updateAmount(amount.toDoubleOrNull() ?: 0.0)
        }

    fun updateTransactionAt(transactionAt: Long) {
        _transactionEditorInput.updateData {
            it.updateTransactionAt(transactionAt)
        }
    }

    fun selectAccount(account: AmAccount) {
        _transactionEditorInput.updateData { it.selectAccount(account) }
        selectTransactionType(account.defaultTransactionType)
        dismissBottomSheet()
    }

    fun selectTransactionType(transactionType: AmTransactionType) =
        _transactionEditorInput.updateData { it.selectTransactionType(transactionType) }

    fun validateTransaction(): UpsertTransaction? =
        (transactionEditorInput.value as? DataState.Success)?.data?.validateTransactionData()

}

sealed interface TransactionEditorInput {

    val id: String
    val creatorUserId: String
    val title: String
    val subtitle: String
    val selectedAccount: AmAccount?
    val selectedTransactionType: AmTransactionType?
    val amount: Double
    val transactionAtTimestamp: Long
    val transactionAt: String

    fun updateTitle(newTitle: String): TransactionEditorInput
    fun updateSubTitle(newSubtitle: String): TransactionEditorInput
    fun updateAmount(newAmount: Double): TransactionEditorInput
    fun updateTransactionAt(transactionAt: Long): TransactionEditorInput
    fun selectAccount(newAccount: AmAccount): TransactionEditorInput
    fun selectTransactionType(newTransactionType: AmTransactionType): TransactionEditorInput
    fun validateTransactionData(): UpsertTransaction?

    data class TransactionEditorCreate(
        override val id: String = UUID.randomUUID().toString(),
        override val creatorUserId: String,
        override val title: String = "",
        override val subtitle: String = "",
        override val selectedAccount: AmAccount? = null,
        override val selectedTransactionType: AmTransactionType? = null,
        override val amount: Double = 0.0,
        override val transactionAtTimestamp: Long = currentTime(),
        override val transactionAt: String = transactionAtTimestamp.asDate()
    ) : TransactionEditorInput {
        override fun updateTitle(newTitle: String): TransactionEditorInput = copy(title = newTitle)

        override fun updateSubTitle(newSubtitle: String): TransactionEditorInput =
            copy(subtitle = newSubtitle)

        override fun updateAmount(newAmount: Double): TransactionEditorInput =
            copy(amount = newAmount)

        override fun updateTransactionAt(transactionAt: Long): TransactionEditorInput =
            copy(
                transactionAt = transactionAt.asDate(),
                transactionAtTimestamp = transactionAtTimestamp
            )

        override fun selectAccount(newAccount: AmAccount): TransactionEditorInput =
            copy(selectedAccount = newAccount)

        override fun selectTransactionType(newTransactionType: AmTransactionType): TransactionEditorInput =
            copy(selectedTransactionType = newTransactionType)

        override fun validateTransactionData(): UpsertTransaction? =
            if (title.isNotEmpty() && selectedAccount != null && selectedTransactionType != null) {
                UpsertTransaction(
                    id = id, creatorUserId = creatorUserId, title = title, subtitle = subtitle,
                    accountId = selectedAccount.id, amount = amount,
                    transactionType = selectedTransactionType,
                    transactionAt = transactionAt
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
        override val amount: Double,
        override val transactionAtTimestamp: Long = currentTime(),
        override val transactionAt: String = transactionAtTimestamp.asDate()
    ) : TransactionEditorInput {
        override fun updateTitle(newTitle: String): TransactionEditorInput = copy(title = newTitle)

        override fun updateSubTitle(newSubtitle: String): TransactionEditorInput =
            copy(subtitle = newSubtitle)

        override fun updateAmount(newAmount: Double): TransactionEditorInput =
            copy(amount = amount)

        override fun updateTransactionAt(transactionAt: Long): TransactionEditorInput =
            copy(
                transactionAt = transactionAt.asDate(),
                transactionAtTimestamp = transactionAtTimestamp
            )

        override fun selectAccount(newAccount: AmAccount): TransactionEditorInput =
            copy(selectedAccount = newAccount)

        override fun selectTransactionType(newTransactionType: AmTransactionType): TransactionEditorInput =
            copy(selectedTransactionType = newTransactionType)


        override fun validateTransactionData(): UpsertTransaction? = if (title.isNotEmpty()) {
            UpsertTransaction(
                id = id, creatorUserId = creatorUserId, title = title, subtitle = subtitle,
                accountId = selectedAccount.id, amount = amount,
                transactionType = selectedTransactionType,
                transactionAt = transactionAt
            )
        } else null
    }

}