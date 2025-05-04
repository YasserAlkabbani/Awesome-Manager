package com.awesome.manager.feature.account.details

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.model.AmAccountWithBalance
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.common.filterSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class AccountDetailsState(
    val setString: String.(value: String) -> Unit,
    val getString: String.(defaultValue: String) -> StateFlow<String>,
    val refreshTransactions: () -> Unit,
    val account: StateFlow<AmState<AmAccountWithBalance>>,
    val transactions: Flow<PagingData<AmTransaction>>,
) {

    val accountDetailsUI = account
        .filterSuccess()
//        .processUIState()

//    private fun Flow<AmAccount>.processUIState() = onEach { account ->
//        dynamicFabAccountDetails(
//            hasEditPermission = account.updatePermission,
//            navigateToCreateTransaction = { navigateToCreateTransaction(account.id) },
//            navigateToEditAccount = { navigateToEditAccount(account.id) },
//            popUp = ::navigatePopBack
//        )
//    }


}