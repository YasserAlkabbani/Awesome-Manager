package com.awesome.manager.feature.transaction.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            savedStateHandle = savedStateHandle,
            transactionTypes = transactionTypeRepository.returnTransactionTypes()
                .flowOn(Dispatchers.Default)
                .stateIn(viewModelScope, SharingStarted.Eagerly, listOf()),
            createTransaction = ::saveTransaction,
            asAccountsSearchResult = {
                flatMapLatest { accountRepository.returnAccounts(it) }
                    .flowOn(Dispatchers.Default)
                    .stateIn(viewModelScope, SharingStarted.Eagerly, listOf())
            },
            asAccount = {
                flatMapLatest {
                    it?.let { accountRepository.returnAccountById(it) } ?: flowOf(null)
                }
                    .flowOn(Dispatchers.Default)
                    .stateIn(viewModelScope, SharingStarted.Eagerly, null)
            },
            asTransactionType = {
                flatMapLatest {
                    it?.let { transactionTypeRepository.returnTransactionTypeById(it) } ?: flowOf(
                        null
                    )
                }
                    .flowOn(Dispatchers.Default)
                    .stateIn(viewModelScope, SharingStarted.Eagerly, null)
            },
        ).apply { fillTransactionData() }

    private fun fillTransactionData() {
        viewModelScope.launch {
            when {
                transactionEditorArg.accountId != null -> {
                    accountRepository.returnAccountById(transactionEditorArg.accountId)
                        .firstOrNull()?.let(transactionEditorState::selectAccount)
                }

                transactionEditorArg.transactionId != null -> {
                    transactionRepository.returnTransactionById(transactionEditorArg.transactionId)
                        .firstOrNull()?.let(transactionEditorState::fillTransactionData)
                }
            }
        }
    }

    private fun saveTransaction() {
        viewModelScope.launch {

            val initTransactionId: String? = transactionEditorArg.transactionId
            val currentUserId = authRepository.currentUserId().first()

            val (transactionId: String, creatorUserId: String?) =
                when (initTransactionId) {
                    null -> UUID.randomUUID().toString() to currentUserId
                    else -> {
                        val creatorUserId = transactionRepository
                            .returnTransactionById(initTransactionId).firstOrNull()?.creatorUserId!!
                        if (creatorUserId == currentUserId)
                            initTransactionId to creatorUserId else initTransactionId to null
                    }
                }
            transactionEditorState
                .validateTransaction(transactionId = transactionId, creatorUserId = creatorUserId)
                ?.let { upsertTransaction ->
                    transactionRepository.upsertTransaction(upsertTransaction)
                    transactionEditorState.startPop()
                }

        }
    }

}