package com.awesome.manager

import com.awesome.manager.core.designsystem.actions.main.AppBarAction
import com.awesome.manager.core.designsystem.actions.main.BottomSheetAction
import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.actions.main.MainState
import com.awesome.manager.core.designsystem.actions.main.NavigationAction
import com.awesome.manager.core.designsystem.actions.main.PickerAction
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class ActivityStateMain(
    val isLogin: StateFlow<Boolean?>,
    val currentUserEmail: StateFlow<String?>,
    val logout: () -> Unit,
) : MainState() {

    fun updateMainState(mainAction: MainAction) {
        Timber.d("TEST_APPBAR UPDATE_ACTION $mainAction")
        when (mainAction) {
            is NavigationAction -> mainAction.applyAction()
            is AppBarAction -> {
                Timber.d("TEST_APPBAR APPLY_ACTION")
                mainAction.applyAction()
            }
            is BottomSheetAction -> mainAction.applyAction()
            is PickerAction -> mainAction.applyAction()
        }
    }

}
