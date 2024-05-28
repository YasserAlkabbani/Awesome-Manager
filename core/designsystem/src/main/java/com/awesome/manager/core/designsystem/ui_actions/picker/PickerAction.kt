package com.awesome.manager.core.designsystem.ui_actions.picker

import com.awesome.manager.core.designsystem.ui_actions.main.MainAction

sealed class PickerAction {

    data object Hide : PickerAction()

    data class PickDate(
        val initTime: Long, val setDate: (Long) -> Unit, val dismiss: () -> Unit
    ) : PickerAction()

}

fun PickerAction?.sendMainAction(sendMainAction: (MainAction) -> Unit, resetAction: () -> Unit) {
    this?.let {
        resetAction()
        sendMainAction(MainAction.Pick(this))
    }
}