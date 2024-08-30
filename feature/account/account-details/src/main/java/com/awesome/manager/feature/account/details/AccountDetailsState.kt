package com.awesome.manager.feature.account.details

import androidx.paging.PagingData
import com.awesome.manager.core.data.states.DataState
import com.awesome.manager.core.designsystem.actions.main.StateManager
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class AccountDetailsState(
    val refreshTransactions: () -> Unit,
    val amAccount: StateFlow<DataState<AmAccount>>,
    val amTransactions: Flow<PagingData<AmTransaction>>,
    val allowToUpdate: StateFlow<DataState<Boolean>>
) : StateManager() {


}