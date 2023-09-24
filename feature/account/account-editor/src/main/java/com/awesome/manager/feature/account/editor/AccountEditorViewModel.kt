package com.awesome.manager.feature.account.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import com.awesome.manager.core.data.repository.transaction_type.TransactionTypeRepository
import com.awesome.manager.feature.account.editor.navigation.AccountEditorArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountEditorViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
    private val savedStateHandle: SavedStateHandle,
    currencyRepository: CurrencyRepository,
    transactionTypeRepository: TransactionTypeRepository,
) : ViewModel() {

    val accountEditorState: AccountEditorState = AccountEditorState(
        savedStateHandle = savedStateHandle,
        account = accountRepository.returnAccountById(AccountEditorArg(savedStateHandle).accountId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null),
        currencies = currencyRepository.returnCurrencies()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf()),
        asSelectedCurrencies = {
            flatMapLatest { currencyRepository.returnCurrencyById(it) }
                .flowOn(Dispatchers.Default)
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
        },
        transactionTypes = transactionTypeRepository.returnTransactionTypes()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf()),
        asSelectedTransactionTypes = {
            flatMapLatest { transactionTypeRepository.returnTransactionTypeById(it) }
                .flowOn(Dispatchers.Default)
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
        },
        onSave = ::onSave,
    )


    init {
        fillAccountData()
    }

    private fun fillAccountData() {
        viewModelScope.launch {
            accountEditorState.account.filterNotNull().collect { account ->
                accountEditorState.fillAccountData(account)
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {

            if (accountEditorState.validateData()) {
                val account =
                    accountRepository.returnAccountById(accountEditorState.account.value?.id)
                        .firstOrNull()
                val currentUserId: String = authRepository.returnCurrentUserId()!!
                val name: String = accountEditorState.name.value
                val imageUrl: String = accountEditorState.imageUrl.value
                val selectedCurrency: String = accountEditorState.selectedCurrency.value?.id!!
                val defaultTransactionType: String =
                    accountEditorState.defaultTransactionType.value?.id!!

                if (currentUserId == account?.creatorUserId) {
                    updateAccount(
                        account.id,
                        currentUserId,
                        name,
                        imageUrl,
                        selectedCurrency,
                        defaultTransactionType
                    )
                } else {
                    createAccount(
                        currentUserId,
                        name,
                        imageUrl,
                        selectedCurrency,
                        defaultTransactionType
                    )
                }
                accountEditorState.onNavigationBack()
            }

        }
    }

    private suspend fun createAccount(
        currentUserId: String,
        name: String,
        imageUrl: String,
        selectedCurrency: String,
        defaultTransactionType: String,
    ) {
        accountRepository.upsertAccount(
            creatorUserId = currentUserId,
            accountName = name,
            imageUrl = imageUrl,
            currencyId = selectedCurrency,
            defaultTransactionTypeId = defaultTransactionType
        )
    }

    private suspend fun updateAccount(
        accountId: String,
        currentUserId: String,
        name: String,
        imageUrl: String,
        selectedCurrency: String,
        defaultTransactionType: String,
    ) {
        accountRepository.upsertAccount(
            accountId = accountId,
            creatorUserId = currentUserId,
            accountName = name,
            imageUrl = imageUrl,
            currencyId = selectedCurrency,
            defaultTransactionTypeId = defaultTransactionType
        )
    }

}