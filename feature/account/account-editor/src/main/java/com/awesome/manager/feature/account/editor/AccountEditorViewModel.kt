package com.awesome.manager.feature.account.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.common.states.asListDataStateFlow
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import com.awesome.manager.core.data.repository.transaction_type.TransactionTypeRepository
import com.awesome.manager.feature.account.editor.navigation.AccountEditorArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AccountEditorViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
    savedStateHandle: SavedStateHandle,
    currencyRepository: CurrencyRepository,
    transactionTypeRepository: TransactionTypeRepository,
) : ViewModel() {


    private val accountEditorArg :AccountEditorArg= AccountEditorArg(savedStateHandle)

    val accountEditorState: AccountEditorState = AccountEditorState(
        currencies = currencyRepository.returnCurrencies().asListDataStateFlow(viewModelScope),
        transactionTypes = transactionTypeRepository.returnTransactionTypes()
            .asListDataStateFlow(viewModelScope),
        onSave = ::onSave,
    )

    init {
        fillAccountData()
        Timber.d("TEST_NAVIGATION VIEWMODEL INIT")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("TEST_NAVIGATION VIEWMODEL CLEAR")
    }

    private fun fillAccountData() {

        viewModelScope.launch {
            val currentUserId = authRepository.currentUserId().first()
            val account = accountEditorArg.accountId
                ?.let { accountRepository.returnAccountById(it).first() }
            when (account) {
                null -> accountEditorState.asCreateAccount(creatorUserId = currentUserId)
                else -> accountEditorState
                    .asEditAccount(currentUserId = currentUserId, amAccount = account)
            }
        }

    }

    private fun onSave() {
        viewModelScope.launch {
            accountEditorState.checkValidateAccount()?.let {
                accountRepository.upsertAccount(it)
                accountEditorState.navigatePopBack()
            }
        }
    }

}