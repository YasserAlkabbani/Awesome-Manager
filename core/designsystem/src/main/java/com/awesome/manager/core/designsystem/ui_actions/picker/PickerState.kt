package com.awesome.manager.core.designsystem.ui_actions.picker

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface MainPicker {
    val pickAction: StateFlow<PickerAction>
    fun PickerAction.sendAction()
    fun resetPick()
    fun pickDate(initTime: Long, setDate: (Long) -> Unit, dismiss: () -> Unit)
    val loading: StateFlow<Boolean>
    fun startLoading()
    fun stopLoading()
}

class MainPickerState : MainPicker {
    private val _pickAction: MutableStateFlow<PickerAction> = MutableStateFlow(PickerAction.Idle)
    override val pickAction: StateFlow<PickerAction> = _pickAction
    override fun PickerAction.sendAction() {
        _pickAction.update { this }
    }

    override fun resetPick() = PickerAction.Idle.sendAction()
    override fun pickDate(initTime: Long, setDate: (Long) -> Unit, dismiss: () -> Unit) =
        PickerAction.PickDate(initTime = initTime, setDate = setDate, dismiss = dismiss)
            .sendAction()

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val loading: StateFlow<Boolean> = _loading
    override fun startLoading() = _loading.update { true }
    override fun stopLoading() = _loading.update { false }
}