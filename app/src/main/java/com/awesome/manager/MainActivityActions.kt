package com.awesome.manager

import com.awesome.manager.core.model.AmUser
import com.awesome.manager.core.ui.actions.main.MainAction
import com.awesome.manager.core.ui.actions.main.ActionsManager
import kotlinx.coroutines.flow.StateFlow

class MainActivityActions(
    val isLogin: StateFlow<Boolean?>,
    val currentUser: StateFlow<AmUser?>,
    val logout: () -> Unit,
) : ActionsManager() {

    fun updateMainState(mainAction: MainAction) {
        mainAction.applyMainAction()
    }

}
