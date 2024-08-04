package com.awesome.manager.feature.account.accounts

import androidx.paging.PagingData
import com.awesome.manager.core.designsystem.actions.main.StateManager
import com.awesome.manager.core.model.AmAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update


class AccountsState(
    searchForAccounts: FilterData.() -> Flow<PagingData<AmAccount>>,
    val refreshAccounts: () -> Unit
) : StateManager() {

    private val _filterData: MutableStateFlow<FilterData> = MutableStateFlow(FilterData())
    val filterData: StateFlow<FilterData> = _filterData.asStateFlow()

    val accounts = filterData.flatMapLatest { it.searchForAccounts() }

    fun updateSearchKey(searchKey: String) =
        _filterData.update { it.updateSearchKey(searchKey = searchKey) }

    fun clearSearch() = _filterData.update { it.clearSearch() }

}

data class FilterData(val searchKey: String? = null) {

    val searchFilter: Boolean = !searchKey.isNullOrEmpty()
    val filterApplauded: Boolean = searchFilter

    fun updateSearchKey(searchKey: String) = copy(searchKey = searchKey.ifBlank { null })
    fun clearSearch() = copy(searchKey = null)

}