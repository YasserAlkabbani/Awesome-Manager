package com.awesome.manager.feature.account.editor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.navigation.toRoute
import com.awesome.manager.core.common.EditorType
import com.awesome.manager.core.common.asStateFlowList
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import com.awesome.manager.core.data.repository.transaction_type.TransactionTypeRepository
import com.awesome.manager.core.designsystem.component.asFlow
import com.awesome.manager.core.model.AmCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ACCOUNT_NAME: String = "ACCOUNT_NAME"
private const val IMAGE_URL: String = "IMAGE_URL"
private const val CURRENCY_ID: String = "CURRENCY_ID"
private const val TRANSACTION_TYPE_ID: String = "TRANSACTION_TYPE_ID"

@HiltViewModel
class AccountEditorViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
    private val transactionTypeRepository: TransactionTypeRepository,
    private val currencyRepository: CurrencyRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private fun String.setState(value: String?): Unit = savedStateHandle.set(this, value)
    private fun String.getState(): StateFlow<String?> = savedStateHandle.getStateFlow(this, null)

    val editorType: EditorType =
        savedStateHandle.toRoute<AccountEditorRoute>().accountID.let { accountID ->
            when (accountID) {
                null -> EditorType.Create
                else -> EditorType.Update(accountID)
            }
        }

    val accountNameTextFieldState: TextFieldState = savedStateHandle.saveable(
        key = ACCOUNT_NAME,
        saver = TextFieldState.Saver,
        init = { TextFieldState("") },
    )

    private val _accountEditorState: MutableStateFlow<AccountEditorState> =
        MutableStateFlow(AccountEditorState.Init)
    val accountEditorState: StateFlow<AccountEditorState> = _accountEditorState

    private val _accountEditorEvent: MutableStateFlow<AccountEditorEvents> =
        MutableStateFlow(AccountEditorEvents.Idle)
    internal val accountEditorEvent: StateFlow<AccountEditorEvents> =
        _accountEditorEvent.asStateFlow()
    fun doneAccountEditorEvent() = _accountEditorEvent.update { AccountEditorEvents.Idle }
    fun navigateBack() = _accountEditorEvent.update { AccountEditorEvents.Popup }

    val imageURL: StateFlow<String?> = IMAGE_URL.getState()
    fun setImageURL(imageURL: String?) = IMAGE_URL.setState(imageURL)

    val currencyID: StateFlow<String?> = CURRENCY_ID.getState()
    fun setCurrencyID(currencyID: String) = CURRENCY_ID.setState(currencyID)

    val transactionTypeID: StateFlow<String?> = TRANSACTION_TYPE_ID.getState()
    fun setTransactionTypeID(transactionTypeID: String) =
        TRANSACTION_TYPE_ID.setState(transactionTypeID)

    val transactionTypes: StateFlow<List<AmTransactionType>> =
        transactionTypeRepository.returnTransactionsTypes().asStateFlowList(viewModelScope)

    val currencies: StateFlow<List<AmCurrency>> =
        currencyRepository.returnCurrencies().asStateFlowList(viewModelScope)

    init {
        syncAccountEditorState()
    }

    fun syncAccountEditorState() = viewModelScope.launch {

        when (editorType) {
            is EditorType.Create -> {
                setTransactionTypeID(transactionTypes.first().first().id)
                setCurrencyID(currencies.first().first().id)
            }

            is EditorType.Update -> {
                val account =
                    accountRepository.getAccountByID(editorType.accountID).first().account
                accountNameTextFieldState.setTextAndPlaceCursorAtEnd(account.name)
                setTransactionTypeID(account.defaultTransactionTypeID)
                setCurrencyID(account.currencyID)
                account.imageUrl?.let { setImageURL(it) }
            }
        }

        combine(
            accountNameTextFieldState.asFlow(),
            imageURL,
            currencyID,
            transactionTypeID,
        ) { accountName, imageURL, currencyID, transactionTypeID ->

            val validAccountName: Boolean = !accountName.isBlank()
            val validCurrency: Boolean = currencyID != null
            val validTransactionType: Boolean = transactionTypeID != null

            val validateInput = validAccountName && validCurrency && validTransactionType

            when {
                accountName.isBlank() -> AccountEditorState.Init
                validateInput -> AccountEditorState.ValidateInput(
                    accountName = accountName,
                    imageURL = imageURL,
                    defaultTransactionTypeID = transactionTypeID,
                    currencyID = currencyID,
                )

                else -> AccountEditorState.InvalidateInput(
                    invalidAccountName = !validAccountName,
                    invalidTransactionType = !validTransactionType,
                    invalidCurrency = !validCurrency
                )
            }

        }
            .flowOn(Dispatchers.Default)
            .collect { newAccountEditeState -> _accountEditorState.update { newAccountEditeState } }
    }

    fun saveAccount(accountEditorState: AccountEditorState.ValidateInput) {
        viewModelScope.launch(Dispatchers.Default) {
            when (editorType) {
                is EditorType.Create -> accountRepository.createAccount(
                    name = accountEditorState.accountName,
                    creatorUserID = authRepository.currentUser().first().id,
                    currencyID = accountEditorState.currencyID,
                    defaultTransactionTypeID = accountEditorState.defaultTransactionTypeID,
                    imageUrl = accountEditorState.imageURL,
                )

                is EditorType.Update -> accountRepository.updateAccount(
                    accountID = editorType.accountID,
                    name = accountEditorState.accountName,
                    currencyID = accountEditorState.currencyID,
                    defaultTransactionTypeID = accountEditorState.defaultTransactionTypeID,
                    imageUrl = accountEditorState.imageURL,
                )
            }
            navigateBack()
        }
    }
}