package com.awesome.manager.feature.transaction.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.data.repository.transaction_type.TransactionTypeRepository
import com.awesome.manager.core.ui.ChipData
import com.awesome.manager.feature.transaction.editor.navigation.TransactionEditorArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TransactionEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    accountRepository: AccountRepository,
    transactionTypeRepository: TransactionTypeRepository,
    private val transactionRepository: TransactionRepository,
) : ViewModel() {

    private val transactionEditorArg= TransactionEditorArg(savedStateHandle)

    val transactionEditorState: TransactionEditorState by lazy {
        TransactionEditorState(
            savedStateHandle = savedStateHandle,
            transactionTypes = transactionTypeRepository.returnTransactionTypes()
                .distinctUntilChanged()
                .filterNotNull().map { it.map { ChipData(it.id,it.title) } }
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
            createTransaction = ::createTransaction
        )
    }


    private fun createTransaction(){

    }

}