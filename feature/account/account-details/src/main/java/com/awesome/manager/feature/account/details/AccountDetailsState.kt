package com.awesome.manager.feature.account.details

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.ui.filterSuccessData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class AccountDetailsState(
    val setString: String.(value: String) -> Unit,
    val getString: String.(defaultValue: String) -> StateFlow<String>,
    val refreshTransactions: () -> Unit,
    val account: StateFlow<AmUIState<AmAccount>>,
    val transactions: Flow<PagingData<AmTransaction>>,
) {

    val accountDetailsUI = account
        .filterSuccessData()
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