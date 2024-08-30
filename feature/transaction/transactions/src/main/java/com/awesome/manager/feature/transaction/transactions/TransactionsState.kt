package com.awesome.manager.feature.transaction.transactions

import androidx.paging.PagingData
import com.awesome.manager.core.data.extention.asDateRange
import com.awesome.manager.core.designsystem.actions.main.StateManager
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update


class TransactionsState(
    val refreshTransactions: () -> Unit,
    val searchForTransaction: FilterData.() -> Flow<PagingData<AmTransaction>>
) : StateManager() {

    private val _filterData: MutableStateFlow<FilterData> = MutableStateFlow(FilterData())
    val filterData: StateFlow<FilterData> = _filterData.asStateFlow()

    val transactionTypes = AmTransactionType.entries.toList()

    val transactions = filterData.flatMapLatest { it.searchForTransaction() }

    fun updateSearchKey(searchKey: String) =
        _filterData.update { it.updateSearchKey(searchKey) }

    fun clearSearch() = _filterData.update { it.clearSearch() }
    fun updateDate(fromDate: Long, toDate: Long) =
        _filterData.update { it.updateDate(fromDate to toDate) }

    fun clearDate() = _filterData.update { it.clearDate() }
    fun updateTransactionType(transactionType: AmTransactionType) =
        _filterData.update { it.updateTransactionType(transactionType) }

    fun clearTransactionType() =
        _filterData.update { it.clearTransactionType() }

}

data class FilterData(
    val searchKey: String? = null,
    val transactionType: AmTransactionType? = null,
    val date: Pair<Long, Long>? = null,
) {

    val searchFilter: Boolean = !searchKey.isNullOrEmpty()
    val dateFilter: Boolean = date != null
    val transactionTypeFilter: Boolean = transactionType != null
    val filterApplauded: Boolean = searchFilter || dateFilter || transactionTypeFilter

    val dateString: String? = date?.asDateRange()

    fun updateSearchKey(searchKey: String) = copy(searchKey = searchKey.ifBlank { null })
    fun clearSearch() = copy(searchKey = null)

    fun updateDate(newDate: Pair<Long, Long>) = copy(date = newDate)
    fun clearDate() = copy(date = null)

    fun updateTransactionType(transactionType: AmTransactionType) =
        copy(transactionType = transactionType)

    fun clearTransactionType() = copy(transactionType = null)

}