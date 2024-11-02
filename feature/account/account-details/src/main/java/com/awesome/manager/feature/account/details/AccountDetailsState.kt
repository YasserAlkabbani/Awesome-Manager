package com.awesome.manager.feature.account.details

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.ui.actions.main.ActionsManager
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class AccountDetailsState(
    val refreshTransactions: () -> Unit,
    val amAccount: StateFlow<AmUIState<AmAccount>>,
    val amTransactions: Flow<PagingData<AmTransaction>>,
    val allowToUpdate: StateFlow<AmUIState<Boolean>>
) : ActionsManager() {


}