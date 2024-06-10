package com.awesome.manager.feature.transaction.editor

import androidx.paging.PagingData
import com.awesome.manager.core.common.enums.EditorInputType
import com.awesome.manager.core.common.extentions.asDate
import com.awesome.manager.core.common.extentions.currentTime
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.common.states.setData
import com.awesome.manager.core.common.states.updateData
import com.awesome.manager.core.designsystem.actions.main.MainState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import java.util.UUID

class TransactionEditorStateMain(
    val accountsSearchResults: String.() -> Flow<PagingData<AmAccount>>,
    val createTransaction: () -> Unit,
) : MainState() {

    val transactionTypes = AmTransactionType.entries.toList()

    private val _transactionEditorInput: MutableStateFlow<DataState<TransactionEditorInput>> =
        MutableStateFlow(DataState.Loading)
    val transactionEditorInput: StateFlow<DataState<TransactionEditorInput>> =
        _transactionEditorInput

    private val transactionFilterData: MutableStateFlow<TransactionFilterData> =
        MutableStateFlow(TransactionFilterData())

    fun updateSearchKey(searchKey: String) {
        transactionFilterData.update { it.copy(searchKey = searchKey) }
    }

    fun asCreateTransaction(creatorUserId: String) {
        _transactionEditorInput.setData {
            TransactionEditorInput(
                id = UUID.randomUUID().toString(), creatorUserId = creatorUserId,
                title = "", subtitle = "",
                selectedAccount = null, selectedTransactionType = null,
                amount = 0.0, transactionAtTimestamp = currentTime(),
                editorInputType = EditorInputType.Create
            )
        }
    }

    val accountsList: Flow<PagingData<AmAccount>> =
        transactionFilterData.flatMapLatest { it.searchKey.accountsSearchResults() }

    private val _searchForAnAccountBottomSheet: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val searchForAnAccountBottomSheet: StateFlow<Boolean> =
        _searchForAnAccountBottomSheet.asStateFlow()

    fun requestSearchForAnAccountBottomSheet() =
        _searchForAnAccountBottomSheet.update { true }

    fun doneSearchForAnAccountBottomSheet() =
        _searchForAnAccountBottomSheet.update { false }

    fun asEditTransaction(transaction: AmTransaction, account: AmAccount) {
        _transactionEditorInput.setData {
            TransactionEditorInput(
                id = transaction.id,
                creatorUserId = transaction.creatorUserId,
                title = transaction.title,
                subtitle = transaction.subtitle,
                selectedAccount = account,
                selectedTransactionType = transaction.transactionType,
                amount = transaction.amount,
                transactionAtTimestamp = transaction.transactionAt,
                editorInputType = EditorInputType.Edit
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
    }

    fun selectTransactionType(transactionType: AmTransactionType) =
        _transactionEditorInput.updateData { it.selectTransactionType(transactionType) }

    fun validateTransaction(): UpsertTransaction? =
        (transactionEditorInput.value as? DataState.Success)?.data?.validateTransactionData

}

data class TransactionEditorInput(
    val id: String,
    val creatorUserId: String,
    val title: String,
    val subtitle: String,
    val amount: Double,
    val selectedAccount: AmAccount?,
    val selectedTransactionType: AmTransactionType?,
    val transactionAtTimestamp: Long = currentTime(),
    val editorInputType: EditorInputType
) {

    val transactionAtDate: String = transactionAtTimestamp.asDate()

    fun updateTitle(newTitle: String): TransactionEditorInput =
        copy(title = newTitle)

    fun updateSubTitle(newSubtitle: String): TransactionEditorInput =
        copy(subtitle = newSubtitle)

    fun updateAmount(newAmount: Double): TransactionEditorInput =
        copy(amount = newAmount)

    fun selectAccount(newAccount: AmAccount): TransactionEditorInput =
        copy(selectedAccount = newAccount)

    fun updateTransactionAt(transactionAt: Long): TransactionEditorInput =
        copy(
            transactionAtTimestamp = transactionAt
        )

    fun selectTransactionType(newTransactionType: AmTransactionType): TransactionEditorInput =
        copy(selectedTransactionType = newTransactionType)

    val validateTransactionData: UpsertTransaction? =
        if (selectedAccount != null && selectedTransactionType != null) {
            UpsertTransaction(
                id = id, creatorUserId = creatorUserId, title = title, subtitle = subtitle,
                accountId = selectedAccount.id, amount = amount,
                transactionType = selectedTransactionType, transactionAt = transactionAtTimestamp
            )
        } else null
}

data class TransactionFilterData(
    val searchKey: String = ""
)