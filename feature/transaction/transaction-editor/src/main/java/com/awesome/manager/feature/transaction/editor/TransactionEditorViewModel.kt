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
import com.awesome.manager.core.common.asStateFlow
import com.awesome.manager.core.common.asAmState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountRepository: AccountRepository,
    private val authRepository: AuthRepository,
    private val transactionRepository: TransactionRepository,
) : ViewModel() {

    private val transactionEditorRouteArg: TransactionEditorRoute =
        savedStateHandle.toRoute()
    private val transactionID: String? = transactionEditorRouteArg.transactionID
    private val accountID: String? = transactionEditorRouteArg.accountID

    private val transactionEditorData = when (transactionID) {
        null -> authRepository.currentUser()
            .map { currentUser ->
                val transactionType: AmTransactionType? = when (accountID) {
                    null -> null
                    else -> accountRepository.getAccountByID(accountID)
                        .first().account.defaultTransactionType
                }
                TransactionEditorData.CreateTransaction(
                    creatorUserID = currentUser.id,
                    accountID = accountID,
                    defaultTransactionType = transactionType
                )
            }

        else -> transactionRepository.getTransactionById(transactionID)
            .map { transaction ->
                TransactionEditorData.EditTransaction(
                    creatorUserID = transaction.creatorUserID,
                    transactionID = transaction.transactionID,
                    accountID = transaction.creatorUserID,
                    defaultTransactionType = transaction.transactionType,
                    transaction = transaction,
                )
            }
    }.asAmState(scope = viewModelScope)

    val transactionEditorState: TransactionEditorState =
        TransactionEditorState(
            setString = { savedStateHandle[this] = it },
            getString = { savedStateHandle.getStateFlow(this, it) },
            setLong = { savedStateHandle[this] = it },
            getLong = { savedStateHandle.getStateFlow(this, it) },
            upsertTransaction = { upsertTransaction() },
            accountsSearchResults = { accountRepository.getAccounts(this) },
            transactionEditorData = transactionEditorData,
            transactionTypes = AmTransactionType.getTypes(),
            getAccountById = {
                filter { it.isNotBlank() }
                    .flatMapLatest { accountRepository.getAccountByID(it) }
                    .asStateFlow(viewModelScope, null)
            },
        )


    private fun UpsertTransaction.upsertTransaction() {
        viewModelScope.launch {
//            transactionRepository.upsertTransaction(this@upsertTransaction)
//            transactionEditorState.navigateToTransactionDetails(
//                accountID = accountId,
//                transactionID = transactionID
//            )
        }
    }

}