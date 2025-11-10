package com.awesome.manager.feature.account.details

import com.awesome.manager.core.model.AmAccountWithDetails

internal sealed interface AccountDetailsState {

    data object Loading : AccountDetailsState

    data object Error : AccountDetailsState

    data class Success(
        val amAccountWithDetails: AmAccountWithDetails
    ) : AccountDetailsState

}

internal sealed interface AccountTransactionsState {

    data object Loading : AccountTransactionsState

    data object Error : AccountTransactionsState

    data object Success : AccountTransactionsState

}

internal sealed interface AccountDetailsEvent  {
    data class CreateTransaction(val accountID: String) : AccountDetailsEvent
    data class EditAccount(val accountID: String) : AccountDetailsEvent
    data object Popup : AccountDetailsEvent
    data object Idle : AccountDetailsEvent
}