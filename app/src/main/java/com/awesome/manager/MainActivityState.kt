package com.awesome.manager

import com.awesome.manager.core.designsystem.ui_actions.AppBarAction
import com.awesome.manager.core.designsystem.ui_actions.BottomSheetAction
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.core.designsystem.ui_actions.MainActionsState
import com.awesome.manager.core.designsystem.ui_actions.NavigationAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainActivityState(
    val isLogin: StateFlow<Boolean>,
    val currentUserEmail: StateFlow<String>,
    val logout: () -> Unit,
):MainActionsState() {

    private val _navigationState: MutableStateFlow<NavigationAction> = MutableStateFlow(NavigationAction.Idle)
    val navigationState: StateFlow<NavigationAction> = _navigationState

    private val _appBarState:MutableStateFlow<AppBarAction> = MutableStateFlow(AppBarAction.Idle)
    val appBarState:StateFlow<AppBarAction> = _appBarState

    private val _bottomSheetState:MutableStateFlow<BottomSheetAction> =MutableStateFlow(
        BottomSheetAction.Empty)
    val bottomSheetState:StateFlow<BottomSheetAction> = _bottomSheetState

    fun sendMainAction(mainAction: MainActions){
        when(mainAction){
            is MainActions.Navigate -> _navigationState.update { mainAction.navigationAction }
            is MainActions.AppBar -> _appBarState.update { mainAction.appBarAction }
            is MainActions.BottomSheet -> _bottomSheetState.update { mainAction.bottomSheetAction }
        }
    }

}
