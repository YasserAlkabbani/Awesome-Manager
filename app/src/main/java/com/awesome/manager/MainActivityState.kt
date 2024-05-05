package com.awesome.manager

import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.core.designsystem.ui_actions.MainActionsState
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

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
            is MainActions.Pick -> mainAction.pickAction.sendAction()
        }
    }

}
