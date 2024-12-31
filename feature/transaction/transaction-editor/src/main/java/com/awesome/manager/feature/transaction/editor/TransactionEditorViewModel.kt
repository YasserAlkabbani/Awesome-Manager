package com.awesome.manager.feature.transaction.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertTransaction
import com.awesome.manager.core.ui.actions.asStateFlow
import com.awesome.manager.core.ui.actions.asUIState
import com.awesome.manager.core.ui.actions.main.NavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TransactionEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountRepository: AccountRepository,
    private val authRepository: AuthRepository,
    private val transactionRepository: TransactionRepository,
) : ViewModel() {

    private val transactionEditorArg: NavigationAction.TransactionEditor =
        savedStateHandle.toRoute()
    private val transactionID: String? = transactionEditorArg.transactionId
    private val accountID: String? = transactionEditorArg.accountId

    private val transactionEditorData = when (transactionID) {
        null -> authRepository.currentUser()
            .map { currentUser ->
                val transactionType: AmTransactionType? = when (accountID) {
                    null -> null
                    else -> accountRepository.returnAccountById(accountID)
                        .first()?.defaultTransactionType
                }
                TransactionEditorData.CreateTransaction(
                    creatorUserID = currentUser.id,
                    accountID = accountID,
                    defaultTransactionType = transactionType
                )
            }

        else -> transactionRepository.returnTransactionById(transactionID)
            .map { transaction ->
                TransactionEditorData.EditTransaction(
                    creatorUserID = transaction.creatorUserID,
                    transactionID = transaction.id,
                    accountID = transaction.creatorUserID,
                    defaultTransactionType = transaction.transactionType,
                    transaction = transaction,
                )
            }
    }.asUIState(scope = viewModelScope)

    val transactionEditorState: TransactionEditorState =
        TransactionEditorState(
            setString = { savedStateHandle[this] = it },
            getString = { savedStateHandle.getStateFlow(this, it) },
            setLong = { savedStateHandle[this] = it },
            getLong = { savedStateHandle.getStateFlow(this, it) },
            upsertTransaction = { upsertTransaction() },
            accountsSearchResults = { accountRepository.returnAccounts(this) },
            transactionEditorData = transactionEditorData,
            transactionTypes = AmTransactionType.getTypes(),
            getAccountById = {
                flatMapLatest { accountRepository.returnAccountById(it) }
                    .asStateFlow(viewModelScope, null)
            },
        )


    private fun UpsertTransaction.upsertTransaction() {
        viewModelScope.launch {
            transactionRepository.upsertTransaction(this@upsertTransaction)
            transactionEditorState.navigateToTransactionDetails(id)
        }
    }

}