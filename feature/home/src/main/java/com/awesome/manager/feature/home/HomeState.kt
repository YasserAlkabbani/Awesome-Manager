package com.awesome.manager.feature.home

import com.awesome.manager.core.data.states.DataState
import com.awesome.manager.core.designsystem.actions.main.StateManager
import com.awesome.manager.core.model.BalanceDetails
import kotlinx.coroutines.flow.StateFlow


class HomeState(
    val balanceDetails: StateFlow<DataState<List<BalanceDetails>>>
) : StateManager()
