package com.awesome.manager.feature.transaction.editor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.navigation.toRoute
import com.awesome.manager.core.common.EditorType
import com.awesome.manager.core.common.asStateFlowValue
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
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

    private fun <T> String.setValue(value: T): Unit =
        savedStateHandle.set(this, value)

    private fun <T> String.getValue(init: T): StateFlow<T> =
        savedStateHandle.getStateFlow(this, init)

    private val editorType: EditorType =
        savedStateHandle.toRoute<TransactionEditorRoute>().let { (accountID, transactionID) ->
            when {
                accountID != null && transactionID != null ->
                    EditorType.Update(accountID, transactionID)

                else -> EditorType.Create(accountID)
            }
        }

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

    val transactionAt: StateFlow<Long> = TRANSACTION_AT.getValue(currentTime())
    fun updateTransactionAt(transactionAt: Long) = TRANSACTION_AT.setValue(transactionAt)

    val selectedAccountID: StateFlow<String?> = TRANSACTION_ACCOUNT_ID.getValue(null)
    val accountWithDetails: StateFlow<AmAccountWithDetails?> = selectedAccountID
        .filterNotNull()
        .flatMapLatest { accountID -> accountRepository.getAccountByID(accountID) }
        .onEach { updateTransactionTypeID(it.account.defaultTransactionTypeID) }
        .asStateFlowValue(viewModelScope)

    fun updateAccountID(accountID: String) = TRANSACTION_ACCOUNT_ID.setValue(accountID)

    val transactionTypeID: StateFlow<String?> = TRANSACTION_TYPE_ID.getValue(null)
    fun updateTransactionTypeID(transactionTypeID: String) =
        TRANSACTION_TYPE_ID.setValue(transactionTypeID)

    private val _transactionEditorState: MutableStateFlow<TransactionEditorState> =
        MutableStateFlow(TransactionEditorState.Init)
    internal val transactionEditorState: StateFlow<TransactionEditorState> =
        _transactionEditorState.asStateFlow()

    private val _transactionEditorEvents: MutableStateFlow<TransactionEditorEvents> =
        MutableStateFlow(TransactionEditorEvents.Idle)
    internal val transactionEditorEvents: StateFlow<TransactionEditorEvents> =
        _transactionEditorEvents.asStateFlow()

    fun navigateBack() = _transactionEditorEvents.update { TransactionEditorEvents.PopupNavigation }
    fun doneTransactionEditorAction() =
        _transactionEditorEvents.update { TransactionEditorEvents.Idle }

    init {
        syncTransactionsEditorState()
    }

    fun syncTransactionsEditorState() {
        viewModelScope.launch {

            when (editorType) {
                is EditorType.Create -> editorType.accountID?.let { updateAccountID(it) }
                is EditorType.Update -> {

                    updateAccountID(editorType.accountID)
                    val transaction = transactionRepository
                        .returnTransactionByID(editorType.transactionID)
                        .first().transaction

                    titleTextFieldState.setTextAndPlaceCursorAtEnd(transaction.title)
                    subTitleTextFieldState.setTextAndPlaceCursorAtEnd(transaction.subtitle)
                    amountTextFieldState.setTextAndPlaceCursorAtEnd(transaction.amount.toString())

                    updateTransactionAt(transaction.transactionAt)
                    updateTransactionTypeID(transaction.transactionTypeID)

                }
            }
            combine(
                titleTextFieldState.asFlow(),
                subTitleTextFieldState.asFlow(),
                amountTextFieldState.asFlow(),
                selectedAccountID,
                transactionTypeID
            ) { it }.combine(transactionAt) { (title, subtitle, amount, accountID, transactionTypeID), transactionAt ->

                val isValidTitle: Boolean = !title.isNullOrBlank()
                val isValidAmount: Boolean = !amount.isNullOrBlank()
                val isValidAccountID: Boolean = !accountID.isNullOrBlank()
                val isValidTransactionType: Boolean = !transactionTypeID.isNullOrBlank()

                val isValidInput =
                    isValidTitle && isValidAmount && isValidAccountID && isValidTransactionType

                when {
                    title.isNullOrBlank() -> TransactionEditorState.Init
                    isValidInput -> TransactionEditorState.Validated(
                        accountID = accountID,
                        title = title,
                        subtitle = subtitle.orEmpty(),
                        amount = amount,
                        transactionTypeID = transactionTypeID,
                        transactionAt = transactionAt,
                    )

                    else -> TransactionEditorState.InvalidatedInput(
                        isValidTitle = isValidTitle,
                        isValidAmount = isValidAmount,
                        isValidAccountID = isValidAccountID,
                        isValidTransactionType = isValidTransactionType
                    )
                }
            }
                .flowOn(Dispatchers.Default)
                .collect { transactionEditorState ->
                    _transactionEditorState.update { transactionEditorState }
                }

        }
    }

    fun saveTransaction(transactionEditorState: TransactionEditorState.Validated) {
        viewModelScope.launch(Dispatchers.Default) {
            when (editorType) {
                is EditorType.Create -> transactionRepository.createTransaction(
                    creatorUserID = authRepository.currentUser().first().id,
                    accountID = transactionEditorState.accountID,
                    transactionTypeID = transactionEditorState.transactionTypeID,
                    title = transactionEditorState.title,
                    subtitle = transactionEditorState.subtitle,
                    amount = transactionEditorState.amount.toDouble(),
                    transactionAt = transactionEditorState.transactionAt,
                )

                is EditorType.Update -> transactionRepository.updateTransaction(
                    transactionID = editorType.transactionID,
                    transactionTypeID = transactionEditorState.transactionTypeID,
                    title = transactionEditorState.title,
                    subtitle = transactionEditorState.subtitle,
                    amount = transactionEditorState.amount.toDouble(),
                    transactionAt = transactionEditorState.transactionAt,
                )
            }
        }
    }

}