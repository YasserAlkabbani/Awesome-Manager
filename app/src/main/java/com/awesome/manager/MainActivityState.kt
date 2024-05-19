package com.awesome.manager

import com.awesome.manager.core.designsystem.ui_actions.main.MainAction
import com.awesome.manager.core.designsystem.ui_actions.main.MainState
import kotlinx.coroutines.flow.StateFlow

class MainActivityState(
    val isLogin: StateFlow<Boolean>,
    val currentUserEmail: StateFlow<String>,
    val logout: () -> Unit,
) : MainState() {

    fun updateMainState(mainAction: MainAction) {
        when (mainAction) {
            is MainAction.Navigate -> mainAction.navigationAction.sendAction()
            is MainAction.AppBar -> mainAction.appBarAction.sendAction()
            is MainAction.BottomSheet -> mainAction.bottomSheetAction.sendAction()
            is MainAction.Pick -> mainAction.pickAction.sendAction()
        }
    }

}
