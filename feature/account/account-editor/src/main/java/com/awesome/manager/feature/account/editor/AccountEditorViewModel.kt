package com.awesome.manager.feature.account.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.awesome.manager.core.data.states.asListDataStateFlow
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.auth.AuthRepository
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import com.awesome.manager.core.data.repository.transaction.TransactionRepository
import com.awesome.manager.core.designsystem.actions.navigation.NavigationDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountEditorViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle,
    currencyRepository: CurrencyRepository,
) : ViewModel() {


    private val accountEditorArg: NavigationDestination.AccountEditor = savedStateHandle.toRoute()

    val accountEditorState: AccountEditorState = AccountEditorState(
        currencies = currencyRepository.returnCurrencies().asListDataStateFlow(viewModelScope),
        onSave = ::onSave,
    )

    init {
        fillAccountData()
    }

    private fun fillAccountData() {

        viewModelScope.launch {
            val currentUserId = authRepository.currentUserId().first()
            val account = accountEditorArg.accountId
                ?.let { accountRepository.returnAccountById(it).first() }
            when (account) {
                null -> accountEditorState.asCreateAccount(creatorUserId = currentUserId)
                else -> {
                    val allowToUpdateCurrency =
                        transactionRepository.returnTransactionCount(account.id) == 0
                    accountEditorState
                        .asEditAccount(
                            currentUserId = currentUserId, amAccount = account,
                            allowToUpdateCurrency = allowToUpdateCurrency
                        )
                }
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