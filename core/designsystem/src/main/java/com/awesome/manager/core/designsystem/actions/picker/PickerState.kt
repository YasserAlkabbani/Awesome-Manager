package com.awesome.manager.core.designsystem.actions.picker

import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.actions.main.PickerAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface PickerStateI {
    val pickerAction: StateFlow<PickerAction?>
    fun PickerAction.applyAction()
    fun donePickerAction()

}

class PickerState : PickerStateI {

    private val _pickerAction: MutableStateFlow<PickerAction?> = MutableStateFlow(null)
    override val pickerAction: StateFlow<PickerAction?> = _pickerAction.asStateFlow()

    override fun PickerAction.applyAction() = _pickerAction.update { this }
    override fun donePickerAction() = _pickerAction.update { null }

}

fun PickerAction?.sendMainAction(
    sendMainAction: (MainAction) -> Unit, donePickerAction: () -> Unit
) {
    this?.let {
        donePickerAction()
        sendMainAction(this)
    }
}