package com.awesome.manager.feature.transaction.editor

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.common.asDate
import com.awesome.manager.core.common.currentTime
import com.awesome.manager.core.ui.actions.setData
import com.awesome.manager.core.ui.actions.updateData
import com.awesome.manager.core.ui.actions.main.ActionsManager
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

class TransactionEditorActions(
    override val setString: String.(value: String) -> Unit,
    override val getString: String.(defaultValue: String) -> StateFlow<String>,
    val accountsSearchResults: String.() -> Flow<PagingData<AmAccount>>,
    val createTransaction: () -> Unit,
) : ActionsManager() {

    val transactionTypes = AmTransactionType.entries.toList()

    private val _transactionEditorInput: MutableStateFlow<AmUIState<TransactionEditorInput>> =
        MutableStateFlow(AmUIState.Loading())
    val transactionEditorInput: StateFlow<AmUIState<TransactionEditorInput>> =
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
        (transactionEditorInput.value as? AmUIState.Success)?.data?.validateTransactionData

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

enum class EditorInputType {
    Create, Edit
}