package com.awesome.manager.feature.home

import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.ui.actions.main.ActionsManager
import com.awesome.manager.core.model.BalanceDetails
import kotlinx.coroutines.flow.StateFlow


class HomeState(
    override val setString: String.(value: String) -> Unit,
    override val getString: String.(defaultValue: String) -> StateFlow<String>,
    val balanceDetails: StateFlow<AmUIState<List<BalanceDetails>>>,
) : ActionsManager()
