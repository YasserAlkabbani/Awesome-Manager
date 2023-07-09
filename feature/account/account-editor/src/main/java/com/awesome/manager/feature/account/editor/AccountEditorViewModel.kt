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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountEditorViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
    currencyRepository: CurrencyRepository,
    transactionTypeRepository: TransactionTypeRepository,
    savedStateHandle: SavedStateHandle,
):ViewModel() {

    val accountEditorState:AccountEditorState=AccountEditorState(
        savedStateHandle = savedStateHandle,
        coroutineScope = viewModelScope,
        currencies = currencyRepository.returnCurrencies().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf()),
        transactionTypes = transactionTypeRepository.returnTransactionTypes().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf()),
        onSave = ::onSave,
    )

    init {
        AccountEditorArg(savedStateHandle).let {

        }
    }

    private fun onSave(){
        viewModelScope.launch {

            if (accountEditorState.validateData()){
                val currentUserId:String=authRepository.returnCurrentUserId()!!
                val name:String=accountEditorState.name.value
                val imageUrl:String=accountEditorState.imageUrl.value
                val selectedCurrency:String=accountEditorState.selectedCurrency.value?.id!!
                val defaultTransactionType:String=accountEditorState.defaultTransactionType.value?.id!!
                accountRepository.createAccount(
                    creatorUserId = currentUserId,
                    accountName = name,
                    imageUrl = imageUrl,
                    currencyId = selectedCurrency,
                    defaultTransactionTypeId = defaultTransactionType
                )
                accountEditorState.onNavigationBack()
            }

        }
    }

}