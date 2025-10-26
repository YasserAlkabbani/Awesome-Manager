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

internal sealed interface AccountDetailsNavigation  {
    data class CreateTransaction(val accountID: String) : AccountDetailsNavigation
    data class EditAccount(val accountID: String) : AccountDetailsNavigation
    data object Popup : AccountDetailsNavigation
}