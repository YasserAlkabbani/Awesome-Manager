package com.awesome.manager.feature.transaction.transactions

import androidx.paging.PagingData
import com.awesome.manager.core.ui.actions.main.ActionsManager
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update


class TransactionsState(
    override val setString: String.(value: String) -> Unit,
    override val getString: String.(defaultValue: String) -> StateFlow<String>,
    val refreshTransactions: () -> Unit,
    val pagingTransactions: Flow<PagingData<AmTransaction>>,
) : ActionsManager() {

}