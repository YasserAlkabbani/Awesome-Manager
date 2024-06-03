package com.awesome.manager.core.designsystem.actions.picker

import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.actions.main.PickerAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface PickerStateI {
    val pickerAction: StateFlow<PickerAction?>
    fun PickerAction.applyAction()
    fun donePickerAction()
    fun hidePick()
    fun pickDate(initTime: Long, setDate: (Long) -> Unit, dismiss: () -> Unit)
}

class PickerState : PickerStateI {

    private val _pickerAction: MutableStateFlow<PickerAction?> = MutableStateFlow(null)
    override val pickerAction: StateFlow<PickerAction?> = _pickerAction

    override fun PickerAction.applyAction() = _pickerAction.update { this }
    override fun donePickerAction() = _pickerAction.update { null }

    override fun hidePick() = PickerAction.Hide.applyAction()

    override fun pickDate(initTime: Long, setDate: (Long) -> Unit, dismiss: () -> Unit) =
        PickerAction.PickDate(initTime = initTime, setDate = setDate, dismiss = dismiss)
            .applyAction()

}

fun PickerAction?.sendMainAction(
    sendMainAction: (MainAction) -> Unit, donePickerAction: () -> Unit
) {
    this?.let {
        donePickerAction()
        sendMainAction(this)
    }
}