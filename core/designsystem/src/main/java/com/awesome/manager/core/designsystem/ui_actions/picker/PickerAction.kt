package com.awesome.manager.core.designsystem.ui_actions.picker

import com.awesome.manager.core.designsystem.ui_actions.main.MainAction

sealed class PickerAction {

    data object Idle : PickerAction()

    data class PickDate(
        val initTime: Long, val setDate: (Long) -> Unit, val dismiss: () -> Unit
    ) : PickerAction()

    fun sendMainAction(sendMainAction: (MainAction) -> Unit, resetAction: () -> Unit) {
        when (this) {
            Idle -> Unit
            is PickDate -> {
                resetAction()
                sendMainAction(MainAction.Pick(this))
            }
        }
    }

}