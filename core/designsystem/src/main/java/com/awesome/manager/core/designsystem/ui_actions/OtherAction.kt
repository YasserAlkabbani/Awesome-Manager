package com.awesome.manager.core.designsystem.ui_actions

sealed class OtherAction {

    data object Idle : OtherAction()

    data class PickDate(val date: () -> String) : OtherAction()

}