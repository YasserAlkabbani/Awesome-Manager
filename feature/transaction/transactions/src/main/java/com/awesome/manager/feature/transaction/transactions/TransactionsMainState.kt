package com.awesome.manager.feature.transaction.transactions

import androidx.paging.PagingData
import com.awesome.manager.core.designsystem.actions.main.MainState
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.Flow


class TransactionsMainState(
    val transactions: Flow<PagingData<AmTransaction>>
) : MainState() {


}