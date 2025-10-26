package com.awesome.manager.feature.transaction.editor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.navigation.toRoute
import com.awesome.manager.core.common.currentTime
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.designsystem.component.asFlow
import com.awesome.manager.core.designsystem.component.asString
import com.awesome.manager.core.model.AmAccountWithDetails
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private const val TRANSACTION_TITLE: String = "TRANSACTION_TITLE"
private const val TRANSACTION_SUB_TITLE: String = "TRANSACTION_SUB_TITLE"
private const val TRANSACTION_AMOUNT: String = "TRANSACTION_AMOUNT"
private const val TRANSACTION_AT: String = "TRANSACTION_AT"
private const val TRANSACTION_ACCOUNT_ID: String = "TRANSACTION_ACCOUNT_ID"
private const val TRANSACTION_TYPE_ID = "TRANSACTION_TYPE_ID"
private const val ACCOUNT_SEARCH_KEY: String = "ACCOUNT_SEARCH_KEY"

@OptIn(ExperimentalUuidApi::class)
@HiltViewModel
class TransactionEditorViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val authRepository: AuthRepository,
    private val transactionRepository: TransactionRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val transactionEditorRouteArg: TransactionEditorRoute = savedStateHandle.toRoute()
    private val accountID: String? = transactionEditorRouteArg.accountID
    private val transactionID: String? = transactionEditorRouteArg.transactionID

    private fun <T> String.setValue(value: T): Unit = savedStateHandle.set(this, value)
    private fun <T> String.getValue(init: T): StateFlow<T> =
        savedStateHandle.getStateFlow(this, init)


    val titleTextFieldState: TextFieldState = savedStateHandle.saveable(
        key = TRANSACTION_TITLE,
        init = { TextFieldState() },
        saver = TextFieldState.Saver
    )
    val subTitleTextFieldState: TextFieldState = savedStateHandle.saveable(
        key = TRANSACTION_SUB_TITLE,
        init = { TextFieldState() },
        saver = TextFieldState.Saver
    )
    val amountTextFieldState: TextFieldState = savedStateHandle.saveable(
        key = TRANSACTION_AMOUNT,
        init = { TextFieldState("0.0") },
        saver = TextFieldState.Saver
    )
    val accountSearchKey: TextFieldState = savedStateHandle.saveable(
        key = ACCOUNT_SEARCH_KEY,
        init = { TextFieldState() },
        saver = TextFieldState.Saver
    )

    private val _transactionEditorState: MutableStateFlow<TransactionEditorState> =
        MutableStateFlow(TransactionEditorState.Init)
    internal val transactionEditorState: StateFlow<TransactionEditorState> =
        _transactionEditorState.asStateFlow()

    private val _transactionEditorNavigation: MutableStateFlow<TransactionEditorNavigation?> =
        MutableStateFlow(null)
    internal val transactionEditorNavigation: StateFlow<TransactionEditorNavigation?> =
        _transactionEditorNavigation

    val transactionAt: StateFlow<Long> = TRANSACTION_AT.getValue(currentTime())
    fun updateTransactionAt(transactionAt: Long) = TRANSACTION_AT.setValue(transactionAt)

    val selectedAccountID: StateFlow<String?> = TRANSACTION_ACCOUNT_ID.getValue(null)
    fun updateAccountID(accountID: String) = TRANSACTION_ACCOUNT_ID.setValue(accountID)
    val accountWithDetails: StateFlow<AmAccountWithDetails?> = selectedAccountID
        .filterNotNull()
        .flatMapLatest { accountID -> accountRepository.getAccountByID(accountID) }
        .onEach { if (transactionTypeID.value == null) setTransactionTypeID(it.account.defaultTransactionTypeID) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(6000),
            initialValue = null
        )

    val transactionTypeID: StateFlow<String?> = TRANSACTION_TYPE_ID.getValue(null)
    fun setTransactionTypeID(transactionTypeID: String) =
        TRANSACTION_TYPE_ID.setValue(transactionTypeID)


    init {
        syncTransactionsEditorState()
    }

    fun syncTransactionsEditorState() {

        viewModelScope.launch {

            when {
                transactionID != null -> {
                    val transaction = transactionRepository
                        .returnTransactionByID(transactionID)
                        .first().transaction

                    titleTextFieldState.setTextAndPlaceCursorAtEnd(transaction.title)
                    subTitleTextFieldState.setTextAndPlaceCursorAtEnd(transaction.subtitle)
                    amountTextFieldState.setTextAndPlaceCursorAtEnd(transaction.amount.toString())

                    updateTransactionAt(transaction.transactionAt)
                    updateAccountID(transaction.accountID)
                    setTransactionTypeID(transaction.transactionTypeID)
                }

                accountID != null -> {
                    updateAccountID(accountID)
                }

                else -> {

                }
            }
            combine(
                titleTextFieldState.asFlow(),
                selectedAccountID,
            ) { title, accountID ->

                val isValidTitle: Boolean = title.isNotBlank()
                val isValidAccountID: Boolean = !accountID.isNullOrBlank()
                val isValidInput = isValidTitle && isValidAccountID

                when {
                    title.isBlank() -> TransactionEditorState.Init
                    isValidInput -> TransactionEditorState.Validated
                    else -> TransactionEditorState.InvalidatedInput(
                        isValidTitle = isValidTitle,
                        isValidAccountID = isValidAccountID
                    )
                }
            }
                .flowOn(Dispatchers.Default)
                .collect()

        }
    }

    fun upsertTransaction() {
        viewModelScope.launch {
            returnUpdatedTransaction()?.let { transaction ->
                transactionRepository.upsertTransaction(transaction)
            }
        }
    }


    suspend fun returnUpdatedTransaction(): AmTransaction? {
        data class TransactionData(
            val transactionID: String,
            val creatorUserID: String,
            val createdAt: Long,
        )

        val currentTime: Long = currentTime()
        val currentUser: AmUser = authRepository.currentUser().first()
        val accountID: String? = selectedAccountID.value
        val transactionTypeID: String? = transactionTypeID.value

        val (transactionID, creatorUserID, createdAt) = when (transactionID) {
            null -> TransactionData(
                transactionID = Uuid.random().toString(),
                creatorUserID = currentUser.id,
                createdAt = currentTime

            )

            else -> transactionRepository.returnTransactionByID(transactionID)
                .first().transaction.let { transaction ->
                    TransactionData(
                        transactionID = transaction.transactionID,
                        creatorUserID = transaction.creatorUserID,
                        createdAt = transaction.createdAt

                    )
                }

        }

        val isValidInput = accountID != null && transactionTypeID != null
        return when (isValidInput) {
            true -> AmTransaction(
                transactionID = transactionID,
                accountID = accountID,
                creatorUserID = creatorUserID,
                transactionTypeID = transactionTypeID,
                title = titleTextFieldState.asString(),
                subtitle = subTitleTextFieldState.asString(),
                amount = amountTextFieldState.asString().toDouble(),
                pending = true,
                createdAt = createdAt,
                updatedAt = currentTime,
                transactionAt = transactionAt.value,
            )

            false -> null
        }
    }

}