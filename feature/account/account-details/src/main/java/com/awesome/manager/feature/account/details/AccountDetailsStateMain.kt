package com.awesome.manager.feature.account.details

import androidx.paging.PagingData
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.actions.main.MainState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class AccountDetailsStateMain(
    val amAccount: StateFlow<DataState<AmAccount>>,
    val amTransactions: Flow<PagingData<AmTransaction>>,
    val allowToUpdate: StateFlow<DataState<Boolean>>
) : MainState() {


}