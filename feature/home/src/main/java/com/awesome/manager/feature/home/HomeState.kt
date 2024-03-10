package com.awesome.manager.feature.home

import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.ui_actions.MainActionsState
import com.awesome.manager.core.model.CurrencyWithBalance
import kotlinx.coroutines.flow.StateFlow


class HomeState(
    val currencyWithData: StateFlow<DataState<List<CurrencyWithBalance>>>
):MainActionsState(){

}
