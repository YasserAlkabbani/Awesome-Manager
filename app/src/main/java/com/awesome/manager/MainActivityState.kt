package com.awesome.manager

import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.actions.main.StateManager
import kotlinx.coroutines.flow.StateFlow

class MainActivityState(
    val isLogin: StateFlow<Boolean?>,
    val currentUserEmail: StateFlow<String?>,
    val logout: () -> Unit,
) : StateManager() {

    fun updateMainState(mainAction: MainAction) {
        mainAction.applyMainAction()
    }

}
