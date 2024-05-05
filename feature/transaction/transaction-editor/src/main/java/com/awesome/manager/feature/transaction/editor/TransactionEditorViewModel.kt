package com.awesome.manager.feature.transaction.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.common.states.asDataStateFlow
import com.awesome.manager.core.common.states.asListDataStateFlow
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.data.repository.transaction_type.TransactionTypeRepository
import com.awesome.manager.feature.transaction.editor.navigation.TransactionEditorArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TransactionEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountRepository: AccountRepository,
    private val authRepository: AuthRepository,
    private val transactionTypeRepository: TransactionTypeRepository,
    private val transactionRepository: TransactionRepository,
) : ViewModel() {

    private val transactionEditorArg: TransactionEditorArg = TransactionEditorArg(savedStateHandle)
    val transactionEditorState: TransactionEditorState =
        TransactionEditorState(
            transactionEditorData = transactionTypeRepository.returnTransactionTypes()
                .map { transactionTypes ->
                    DataState.Success(TransactionEditorData(transactionTypes))
                }
                .asDataStateFlow(viewModelScope),
            createTransaction = ::saveTransaction,
            accountsSearchResults = {
                flatMapLatest { accountRepository.returnAccounts(it) }
                    .asListDataStateFlow(viewModelScope)
            }
        )

    init {
        fillTransactionData()
    }

    private fun fillTransactionData() {
        viewModelScope.launch {
            val currentUserId = authRepository.currentUserId().first()

            val transaction = transactionEditorArg.transactionId?.let {
                transactionRepository.returnTransactionById(it).first()
            }
            when {
                transaction != null -> {
                    val account = accountRepository.returnAccountById(transaction.accountId).first()
                    transactionEditorState.asEditTransaction(transaction, account)
                }

                else -> {
                    val account = transactionEditorArg.accountId?.let {
                        accountRepository.returnAccountById(it).first()
                    }
                    transactionEditorState.asCreateTransaction(currentUserId)
                    account?.let { transactionEditorState.selectAccount(it) }
                }
            }
        }
    }

    private fun saveTransaction() {
        viewModelScope.launch {

            transactionEditorState.validateTransaction()?.let {
                transactionRepository.upsertTransaction(it)
                transactionEditorState.navigatePopBack()
            }

        }
    }

}