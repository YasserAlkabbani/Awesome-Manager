package com.awesome.manager.feature.account.details

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFab
import com.awesome.manager.core.designsystem.component.dynamic_bar.DynamicFabExtraButton
import com.awesome.manager.core.ui.actions.main.ActionsManager
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class AccountDetailsState(
    val refreshTransactions: () -> Unit,
    val account: StateFlow<AmUIState<AmAccount>>,
    val transactions: Flow<PagingData<AmTransaction>>,
) : ActionsManager() {

    val uiState = account
        .processUIState().map { it.data }
        .onEach { account ->
            when (account.updatePermission) {
                true -> dynamicFab(
                    dynamicFab = DynamicFab.AddTransaction {
                        navigateToCreateTransaction(account.id)
                    },
                    dynamicFabExtraButton = DynamicFabExtraButton.Edit {
                        navigateToEditAccount(account.id)
                    }
                )

                false -> dynamicFab(
                    dynamicFab = DynamicFab.AddTransaction {
                        navigateToCreateTransaction(account.id)
                    },
                    dynamicFabExtraButton = null
                )
            }
        }


}