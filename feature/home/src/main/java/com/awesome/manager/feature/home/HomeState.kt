package com.awesome.manager.feature.home

import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.ui_actions.main.MainState
import com.awesome.manager.core.model.BalanceDetails
import kotlinx.coroutines.flow.StateFlow


class HomeState(
    val balanceDetails: StateFlow<DataState<List<BalanceDetails>>>
) : MainState()
