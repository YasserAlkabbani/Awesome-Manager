package com.awesome.manager.feature.transaction.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.data.repository.transaction_type.TransactionTypeRepository
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.feature.transaction.editor.navigation.TransactionEditorArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    accountRepository: AccountRepository,
    private val authRepository: AuthRepository,
    private val transactionTypeRepository: TransactionTypeRepository,
    private val transactionRepository: TransactionRepository,
) : ViewModel() {

    private val transactionEditorArg= TransactionEditorArg(savedStateHandle)

    val transactionEditorState: TransactionEditorState =
        TransactionEditorState(
            savedStateHandle = savedStateHandle,
            transactionTypes = transactionTypeRepository.returnTransactionTypes()
                .flowOn(Dispatchers.Default)
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf()),
            asAccountsSearchResult = {
                flatMapLatest { accountRepository.returnAccounts(it) }
                    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())
            },
            asAccount = {
                    flatMapLatest { accountRepository.returnAccountById(it) }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
            },
            asTransactionType={
                    flatMapLatest { transactionTypeRepository.returnTransactionTypeById(it) }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
            },
            createTransaction = ::createTransaction,
        )


    private fun createTransaction(){
        viewModelScope.launch {
            val currentUserId:String=authRepository.returnCurrentUserId()!!
            val account=transactionEditorState.account.value
            val transactionType=transactionEditorState.transactionType.value
            val title=transactionEditorState.title.value
            val subtitle=transactionEditorState.subtitle.value
            val amount=transactionEditorState.amount.value
            val paymentTransaction=transactionEditorState.paymentTransaction.value

            if (account!=null&&transactionType!=null){
                transactionRepository.createTransaction(
                    creatorUserId =currentUserId,
                    accountId=account.id,
                    title=title,
                    subtitle=subtitle,
                    amount=amount.toDoubleOrNull()?:0.0,
                    transactionTypeId = transactionType.id,
                    paymentTransaction = paymentTransaction
                )
                transactionEditorState.startPop()
            }

        }
    }

}