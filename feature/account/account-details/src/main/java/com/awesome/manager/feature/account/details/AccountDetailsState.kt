package com.awesome.manager.feature.account.details

import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmAccountWithDetails

internal sealed interface AccountDetailsEvent  {
    data class CreateTransactionNavigation(val account: AmAccount) : AccountDetailsEvent
    data class EditAccountNavigation(val account: AmAccount) : AccountDetailsEvent
    data object PopupNavigation : AccountDetailsEvent
    data object Idle : AccountDetailsEvent
}