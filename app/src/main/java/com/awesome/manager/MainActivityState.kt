package com.awesome.manager

import androidx.paging.PagingData
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

private const val ACCOUNT_SEARCH_KEY: String = "ACCOUNT_SEARCH_KEY"

class MainActivityState(
    val setString: String.(value: String) -> Unit,
    val getString: String.(defaultValue: String) -> StateFlow<String>,
    val isLogin: StateFlow<Boolean?>,
    val currentUser: StateFlow<AmUser?>,
    val logout: () -> Unit,
) {

//    fun updateSearchKey(searchKey: String) = ACCOUNT_SEARCH_KEY.setString(searchKey)
//    val searchKey = ACCOUNT_SEARCH_KEY.getString("")
//    val accountsSearchPagingData = searchKey.getAccountsSearchPagingData()

}
