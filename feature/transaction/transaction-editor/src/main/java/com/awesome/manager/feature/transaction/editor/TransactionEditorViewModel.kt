package com.awesome.manager.feature.transaction.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.designsystem.actions.navigation.NavigationDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountRepository: AccountRepository,
    private val authRepository: AuthRepository,
    private val transactionRepository: TransactionRepository,
) : ViewModel() {

    private val transactionEditorArg: NavigationDestination.TransactionEditor =
        savedStateHandle.toRoute()
    val transactionEditorState: TransactionEditorStateMain =
        TransactionEditorStateMain(
            createTransaction = ::saveTransaction,
            accountsSearchResults = { accountRepository.returnAccounts(this) }
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