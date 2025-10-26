package com.awesome.manager.feature.transaction.transactions

import androidx.paging.PagingData
import com.awesome.manager.core.model.AmTransactionWithDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


class TransactionsState(
    val setString: String.(String) -> Unit,
    val getString: String.(String) -> StateFlow<String>,
    val refreshTransactions: () -> Unit,
    val pagingTransactions: Flow<PagingData<AmTransactionWithDetails>>,
){

}