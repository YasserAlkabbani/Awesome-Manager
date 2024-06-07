package com.awesome.manager.feature.account.accounts

import androidx.paging.PagingData
import com.awesome.manager.core.common.extentions.asDate
import com.awesome.manager.core.designsystem.actions.main.MainState
import com.awesome.manager.core.model.AmAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update


class AccountsMainState(
    returnAccount: FilterData.() -> Flow<PagingData<AmAccount>>
) : MainState() {

    private val _filterData: MutableStateFlow<FilterData> = MutableStateFlow(FilterData())
    val filterData: StateFlow<FilterData> = _filterData.asStateFlow()

    fun setDate(fromDate: Long, toDate: Long) {
        _filterData.update { it.copy(date = fromDate to toDate) }
    }

    fun updateSearchKey(searchKey: String) {
        _filterData.update { it.copy(searchKey = searchKey) }
    }

    val accounts = filterData.flatMapLatest { it.returnAccount() }

}

data class FilterData(
    val searchKey: String = "",
    val date: Pair<Long, Long>? = null,
    val positionBalance: Boolean? = null
) {

    fun filterApplauded() = searchKey.isEmpty() || date != null

}