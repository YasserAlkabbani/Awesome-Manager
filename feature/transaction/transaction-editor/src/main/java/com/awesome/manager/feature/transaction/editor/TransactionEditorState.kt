package com.awesome.manager.feature.transaction.editor

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.ui.actions.main.ActionsManager
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertTransaction
import com.awesome.manager.core.ui.actions.filterSuccessData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import java.util.UUID


private const val TRANSACTION_TITLE: String = "TRANSACTION_TITLE"
private const val TRANSACTION_SUB_TITLE: String = "TRANSACTION_SUB_TITLE"
private const val TRANSACTION_AMOUNT: String = "TRANSACTION_AMOUNT"
private const val TRANSACTION_AT: String = "TRANSACTION_AT"
private const val TRANSACTION_ACCOUNT_ID: String = "TRANSACTION_ACCOUNT_ID"
private const val TRANSACTION_TYPE = "TRANSACTION_TYPE"
private const val SEARCH_KEY: String = "SEARCH_KEY"

class TransactionEditorState(
    override val setString: String.(value: String) -> Unit,
    override val getString: String.(defaultValue: String) -> StateFlow<String>,
    val setLong: String.(value: Long) -> Unit,
    val getLong: String.(defaultValue: Long) -> StateFlow<Long>,
    val transactionTypes: List<AmTransactionType>,
    val transactionEditorData: StateFlow<AmUIState<TransactionEditorData>>,
    val accountsSearchResults: String.() -> Flow<PagingData<AmAccount>>,
    private val getAccountById: StateFlow<String>.() -> StateFlow<AmAccount?>,
    private val upsertTransaction: UpsertTransaction.() -> Unit,
) : ActionsManager() {

    fun updateSearchKey(searchKey: String) = SEARCH_KEY.setString(searchKey)
    val searchKey: StateFlow<String> = SEARCH_KEY.getString("")

    fun updateTitle(title: String) = TRANSACTION_TITLE.setString(title)
    val title: StateFlow<String> = TRANSACTION_TITLE.getString("")

    fun updateSubtitle(subtitle: String) = TRANSACTION_SUB_TITLE.setString(subtitle)
    val subtitle: StateFlow<String> = TRANSACTION_SUB_TITLE.getString("")

    fun updateAmount(amount: String) = TRANSACTION_AMOUNT.setString(amount)
    val amount: StateFlow<String> = TRANSACTION_AMOUNT.getString("")

    fun updateTransactionAt(transactionAt: Long) = TRANSACTION_AT.setLong(transactionAt)
    val transactionAt: StateFlow<Long> = TRANSACTION_AT.getLong(System.currentTimeMillis())

    fun updateTransactionType(transactionType: String) = TRANSACTION_TYPE.setString(transactionType)
    val selectedTransactionTypeID: StateFlow<String> =
        TRANSACTION_TYPE.getString(transactionTypes.first().id)

    fun updateAccount(accountID: String) = TRANSACTION_ACCOUNT_ID.setString(accountID)
    val accountID: StateFlow<String> = TRANSACTION_ACCOUNT_ID.getString("")
    val account: StateFlow<AmAccount?> = accountID.getAccountById()

    val transactionEditorUI = transactionEditorData
        .filterSuccessData()
        .setInitData()
        .flatMapLatest { transactionEditorData ->
            combine(title, subtitle, amount, transactionAt, selectedTransactionTypeID, accountID) {
                val title: String = it[0] as String
                val subtitle: String = it[1] as String
                val amount: String = it[2] as String
                val transactionAt: Long = it[3] as Long
                val transactionType: String = it[4] as String
                val accountID: String = it[5] as String
                UpsertTransaction(
                    transactionID = transactionEditorData.transactionID,
                    accountId = accountID,
                    creatorUserId = transactionEditorData.creatorUserID,
                    title = title,
                    subtitle = subtitle,
                    amount = amount.toDoubleOrNull() ?: 0.0,
                    transactionType = transactionType,
                    transactionAt = transactionAt,
                    alreadyOnNetwork = transactionEditorData.alreadyOnNetwork()
                ).processUIState(transactionEditorData)
            }
        }


    private fun Flow<TransactionEditorData>.setInitData() = onEach { transactionEditorData ->
        transactionEditorData.accountID?.let { updateAccount(it) }
        transactionEditorData.defaultTransactionType?.let { updateTransactionType(it.id) }

        when (transactionEditorData) {
            is TransactionEditorData.EditTransaction -> transactionEditorData.transaction.apply {
                updateTitle(title)
                updateSubtitle(subtitle)
                updateAmount(amount.toString())
                updateTransactionAt(transactionAt)
                updateAccount(accountID)
            }

            is TransactionEditorData.CreateTransaction -> Unit
        }
    }

    private fun UpsertTransaction.processUIState(transactionEditorData: TransactionEditorData) {
        when (isValidAccount()) {
            false -> dynamicFabSearchForAccount(
                searchForAccount = {
                    showSearchForAccount(
                        initSearch = account.value?.name.orEmpty(),
                        onSelectAccount = ::updateAccount
                    )
                },
                navigatePopBack = ::navigatePopBack
            )

            true -> when (transactionEditorData) {
                is TransactionEditorData.CreateTransaction -> dynamicFabCreateTransaction(
                    createTransaction = { upsertTransaction() },
                    navigatePopBack = ::navigatePopBack
                )

                is TransactionEditorData.EditTransaction -> dynamicFabUpdateTransaction(
                    updateTransaction = { upsertTransaction() },
                    navigatePopBack = ::navigatePopBack
                )
            }
        }
    }

}

sealed interface TransactionEditorData {

    val transactionID: String
    val creatorUserID: String
    val accountID: String?
    val defaultTransactionType: AmTransactionType?

    fun alreadyOnNetwork(): Boolean = this is EditTransaction && transaction.alreadyOnNetwork

    data class CreateTransaction(
        override val transactionID: String = UUID.randomUUID().toString(),
        override val creatorUserID: String,
        override val accountID: String?,
        override val defaultTransactionType: AmTransactionType?
    ) : TransactionEditorData

    data class EditTransaction(
        override val transactionID: String,
        override val creatorUserID: String,
        override val accountID: String,
        override val defaultTransactionType: AmTransactionType,
        val transaction: AmTransaction,
    ) : TransactionEditorData

}