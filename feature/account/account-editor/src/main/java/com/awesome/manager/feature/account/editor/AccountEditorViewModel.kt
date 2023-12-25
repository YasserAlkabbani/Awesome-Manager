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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AccountEditorViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
    private val savedStateHandle: SavedStateHandle,
    currencyRepository: CurrencyRepository,
    transactionTypeRepository: TransactionTypeRepository,
) : ViewModel() {

    private val accountEditorArg: AccountEditorArg = AccountEditorArg(savedStateHandle)

    val accountEditorState: AccountEditorState = AccountEditorState(
        savedStateHandle = savedStateHandle,
        currencies = currencyRepository.returnCurrencies()
            .stateIn(viewModelScope, SharingStarted.Eagerly, listOf()),
        asSelectedCurrencies = {
            flatMapLatest {
                it?.let { currencyRepository.returnCurrencyById(it) } ?: flowOf(null)
            }
                .flowOn(Dispatchers.Default)
                .stateIn(viewModelScope, SharingStarted.Eagerly, null)
        },
        transactionTypes = transactionTypeRepository.returnTransactionTypes()
            .stateIn(viewModelScope, SharingStarted.Eagerly, listOf()),
        asSelectedTransactionTypes = {
            flatMapLatest {
                it?.let { transactionTypeRepository.returnTransactionTypeById(it) } ?: flowOf(null)
            }
                .flowOn(Dispatchers.Default)
                .stateIn(viewModelScope, SharingStarted.Eagerly, null)
        },
        onSave = ::onSave,
    ).apply { fillAccountData() }

    private fun fillAccountData() {
        viewModelScope.launch {
            accountEditorArg.accountId?.let { initAccountId ->
                accountRepository.returnAccountById(initAccountId).firstOrNull()
                    ?.let(accountEditorState::fillAccountData)
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {

            val initAccountId = accountEditorArg.accountId
            val currentUserId = authRepository.returnCurrentUserId()!!

            val (accountId: String, creatorUserId: String?) = when (initAccountId) {
                null -> UUID.randomUUID().toString() to currentUserId
                else -> {
                    val creatorUserId = accountRepository
                        .returnAccountById(initAccountId).firstOrNull()?.creatorUserId!!
                    if (creatorUserId == currentUserId)
                        initAccountId to creatorUserId else initAccountId to null
                }
            }

            accountEditorState.validateAccount(accountId = accountId, creatorUserId = creatorUserId)
                ?.let { upsertAccount ->
                    accountRepository.upsertAccount(upsertAccount)
                    accountEditorState.onNavigationBack()
                }

        }

    }

}