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
) : MainActionsState() {

    fun updateMainState(mainAction: MainActions) {
        when (mainAction) {
            is MainActions.Navigate -> mainAction.navigationAction.sendAction()
            is MainActions.AppBar -> mainAction.appBarAction.sendAction()
            is MainActions.BottomSheet -> mainAction.bottomSheetAction.sendAction()
        }
    }

}
