package com.awesome.manager.core.designsystem.ui_actions

sealed class PickAction {

    data object Idle : PickAction()

    data class PickDate(
        val initTime: Long, val setDate: (Long) -> Unit, val dismiss: () -> Unit
    ) : PickAction()

    fun sendMainAction(sendMainAction: (MainActions) -> Unit,resetAction:()->Unit) {
        when (this) {
            Idle -> Unit
            is PickDate -> {
                resetAction()
                sendMainAction(MainActions.Pick(this))
            }
        }
    }

}