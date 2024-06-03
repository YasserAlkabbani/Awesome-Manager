package com.awesome.manager.feature.home

import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.actions.main.MainState
import com.awesome.manager.core.model.BalanceDetails
import kotlinx.coroutines.flow.StateFlow


class HomeMainState(
    val balanceDetails: StateFlow<DataState<List<BalanceDetails>>>
) : MainState()
