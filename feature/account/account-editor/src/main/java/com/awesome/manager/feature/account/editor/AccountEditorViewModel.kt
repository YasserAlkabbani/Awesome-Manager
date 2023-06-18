package com.awesome.manager.feature.account.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.manager.core.data.repository.accounts.AccountRepository
import com.awesome.manager.core.data.repository.currency.CurrencyRepository
import com.awesome.manager.core.data.repository.currency.OfflineCurrencyRepository
import com.awesome.manager.feature.account.editor.navigation.AccountEditorArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class AccountEditorViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val currencyRepository: CurrencyRepository,
    savedStateHandle: SavedStateHandle,
):ViewModel() {

    val accountEditorState:AccountEditorState=AccountEditorState(
        savedStateHandle = savedStateHandle,
        coroutineScope = viewModelScope,
        returnCurrencyByCurrencyId = { null },
    )

    init {
        AccountEditorArg(savedStateHandle).let {
            accountEditorState.updateAccountId(it.accountId)
        }
    }

}